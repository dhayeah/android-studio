package com.example.a5progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private Button button;

    int progressStatus = 0;
    private RelativeLayout relativeLayout;
    private Button NextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        relativeLayout = findViewById(R.id.relative_layout);
        NextButton = findViewById(R.id.next);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondDis.class);

                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressStatus = 0;
                showProgressDialogWithTitle("Progressing..", "let's ROCK");
                relativeLayout.setBackgroundResource(R.color.grey_dialog_box);
            }
        });
    }
    private void showProgressDialogWithTitle(String title,String substring) {
        final Handler handler = new Handler();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(substring);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try{
                        Thread.sleep(200);
                        progressStatus += 2;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {

                            progressDialog.setProgress(progressStatus);
                            if (progressStatus < 10){
                                progressDialog.setMessage("Firing up...");
                            }
                            else if (progressStatus < 50){
                                progressDialog.setMessage("Lightning speed...");
                            }
                            else if(progressStatus < 95){
                                progressDialog.setMessage("In a Flash....");
                            }
                            else if (progressStatus < 100){
//                                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                                progressDialog.setMessage("Hurray! Done...");
                            }
                        }
                    });
                }
                hideProgressDialogWithTitle(progressDialog);
            }
        }).start();

    }

    private void hideProgressDialogWithTitle(ProgressDialog progressDialog) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }
}
