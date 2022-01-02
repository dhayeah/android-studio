package com.example.a8smsapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String msgId,body;
    @Override
    public void onReceive(Context context, Intent intent){
        if(SMS_RECEIVED.equals(intent.getAction())){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                Object[] pdus = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i=0;i< pdus.length;i++){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                    }
                    else{
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    }
                    msgId = messages[0].getOriginatingAddress();
                    body = messages[0].getMessageBody();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            context.getApplicationContext(),"Receive"
                    )
                            .setSmallIcon(R.drawable.ic_outline_message)
                            .setContentTitle(msgId)
                            .setContentText(body)
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(1,builder.build());

                }
            }
        }
    }
}
