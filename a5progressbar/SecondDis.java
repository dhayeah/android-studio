package com.example.a5progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondDis extends AppCompatActivity {
    private Button StartButton;
    private EditText TimeEditText;
    private int time;
    private boolean runFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_dis);

        StartButton = findViewById(R.id.btn_start);
        TimeEditText = findViewById(R.id.et_time);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    time = Integer.parseInt(TimeEditText.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(SecondDis.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    runFlag = false;
                }
                if(runFlag){
                    Toast.makeText(SecondDis.this, "...Time to sleep...", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(SecondDis.this)

                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("....Sleep time Completed....")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).show();
                        }
                    }, time*1000);
                }
                runFlag = true;
            }
        });
    }
}



