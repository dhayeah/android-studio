package com.example.graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {
    private Button mButton,nextbutton,btnzoom,btnrotate,btnmove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mButton = (Button) findViewById(R.id.btnback);
        nextbutton=(Button) findViewById(R.id.btnnext);
        btnzoom=(Button)findViewById(R.id.btnzoom);
        btnrotate=(Button)findViewById(R.id.btnrotate);
        btnmove=(Button)findViewById(R.id.btnmove);
        // Set a click listener for Button widget
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(in);
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(in);
            }
        });
        btnzoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView)findViewById(R.id.imageView);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.zoom);
                image.startAnimation(animation1);
            }
        });
        btnrotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.clockwise);
                image.startAnimation(animation);
            }
        });
        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.move);
                image.startAnimation(animation);
            }
        });
}
}