package com.example.myapp7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE = 99;
    private static final int READ_EXTERNAL_STORAGE = 98;
    private Button read, write;
    private EditText filename, contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename = findViewById(R.id.filename_read);
        contents = findViewById(R.id.contents);

        write = findViewById(R.id.btn_write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFile(filename.getText().toString(), contents.getText().toString());
            }
        });

        read = findViewById(R.id.btn_read);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Read.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case WRITE_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    writeFile(filename.getText().toString(), contents.getText().toString());
                }
                else{
                    Toast.makeText(MainActivity.this, "We need write storage permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case READ_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    writeFile(filename.getText().toString(), contents.getText().toString());
                }
                else{
                    Toast.makeText(MainActivity.this, "We need read storage permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    public  void writeFile(String fileName, String body) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            try {
                final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                if (!dir.exists())
                {
                    if(!dir.mkdirs()){
                        Toast.makeText(MainActivity.this, "ALERT could not create the directories", Toast.LENGTH_SHORT).show();
                    }
                }

                final File myFile = new File(dir, fileName + ".txt");
                if (!myFile.exists())
                {
                    myFile.createNewFile();
                }
                FileWriter writer = new FileWriter(myFile);
                writer.append(body);
                writer.flush();
                writer.close();

                Toast.makeText(MainActivity.this, "File stored successfully", Toast.LENGTH_SHORT).show();
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