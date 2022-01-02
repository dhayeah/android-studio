package com.example.a8smsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText phNo, Message;
    TextView tvOutput;
    Button send;
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    SMSBroadcastReceiver sms = new SMSBroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String msg = tvOutput.getText().toString();
            tvOutput.setText(msg+"\n"+msgId+" : "+body);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sms, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sms);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phNo = findViewById(R.id.phNo);
        Message = findViewById(R.id.Message);
        tvOutput = findViewById(R.id.tvOutput);
        send = findViewById(R.id.btnSend);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
        checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Receive","ReceiveName", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        send.setOnClickListener(view -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                }
                else{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                }
            }
        });
    }

    private void sendMessage(){
        String ph = phNo.getText().toString().trim();
        String msg = Message.getText().toString().trim();
        if(!ph.equals("") && !msg.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ph,null,msg,null,null);
            Toast.makeText(MainActivity.this, "SMS sent Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Improper Message Try Later", Toast.LENGTH_SHORT).show();
        }
    }
}