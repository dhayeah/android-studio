package com.example.myapp7;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Read extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE = 99;
    private static final int READ_EXTERNAL_STORAGE = 98;
    private EditText filename;
    private TextView contents;
    private Button read_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);

        filename = findViewById(R.id.filename_read);
        contents = findViewById(R.id.tv_contents);


        read_btn = findViewById(R.id.btn_read_act);

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile(filename.getText().toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case WRITE_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readFile(filename.getText().toString());
                }
                else{
                    Toast.makeText(Read.this, "We need write storage permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case READ_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readFile(filename.getText().toString());
                }
                else{
                    Toast.makeText(Read.this, "We need read storage permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void readFile(String filename){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            try {
                final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                if (!dir.exists())
                {
                    Toast.makeText(Read.this, "ALERT could not create the directories", Toast.LENGTH_SHORT).show();
                }

                final File myFile = new File(dir, filename + ".txt");
                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sdcard/");
                if (!myFile.exists())
                {
                    contents.setTextColor(Color.RED);
                    contents.setGravity(Gravity.CENTER);
                    contents.setText("File not available");
                    return;
                }
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(myFile));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                contents.setGravity(Gravity.LEFT);
                contents.setTextColor(Color.BLACK);
                contents.setText(text.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
            }
        }
    }
}
