package com.example.graphic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {


    private Button mButton,btnmove;
    private ImageView mImageView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        btnmove=(Button)findViewById(R.id.btnmove);
        mButton = (Button) findViewById(R.id.btn);
        mImageView = (ImageView) findViewById(R.id.iv);

        // Set a click listener for Button widget
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ThirdActivity.this,SecondActivity.class);
                startActivity(in);
            }
        });

        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.iv);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.move);
                image.startAnimation(animation);
            }
        });

        // Initialize a new Bitmap object
        Bitmap bitmap = Bitmap.createBitmap(
                300, // Width
                250, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background


        // Initialize a new Paint instance to draw the Rectangle
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
                /*
                    Parameters
                        left : The X coordinate of the left side of the rectangle
                        top : The Y coordinate of the top of the rectangle
                        right : The X coordinate of the right side of the rectangle
                        bottom : The Y coordinate of the bottom of the rectangle

                */
        Rect rectangle = new Rect(
                0, // Left
                30, // Top
                200, // Right
                100// Bottom
        );

        canvas.drawRect(rectangle,paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(50,130,30,paint);
        canvas.drawCircle(150,130,30,paint);
        paint.setColor(Color.RED);
        Rect rectangle2 = new Rect(
                50, // Left
                0, // Top
                150, // Right
                50// Bottom
        );
        canvas.drawRect(rectangle2,paint);

       // canvas.drawLine(100,220,300,220,paint);
       // canvas.drawArc(50,250,100,400,180,120,true,paint);
        mImageView.setImageBitmap(bitmap);

        }

    }
