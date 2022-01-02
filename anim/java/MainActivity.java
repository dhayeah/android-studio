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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Resources mResources;
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;
    private Button mButton;
    private ImageView mImageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the Resources
        mResources = getResources();

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mButton = (Button) findViewById(R.id.btn);
        mImageView = (ImageView) findViewById(R.id.iv);

        // Set a click listener for Button widget
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(in);
            }
        });


                // Initialize a new Bitmap object
                Bitmap bitmap = Bitmap.createBitmap(
                        500, // Width
                        700, // Height
                        Bitmap.Config.ARGB_8888 // Config
                );

                // Initialize a new Canvas instance
                Canvas canvas = new Canvas(bitmap);

                // Draw a solid color to the canvas background
                canvas.drawColor(Color.LTGRAY);

                // Initialize a new Paint instance to draw the Rectangle
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.YELLOW);
                paint.setAntiAlias(true);


                /*

                    Parameters
                        left : The X coordinate of the left side of the rectangle
                        top : The Y coordinate of the top of the rectangle
                        right : The X coordinate of the right side of the rectangle
                        bottom : The Y coordinate of the bottom of the rectangle

                */
                Rect rectangle = new Rect(
                        50, // Left
                        50, // Top
                        100, // Right
                        200// Bottom
                );

                canvas.drawRect(rectangle,paint);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(170,150,50,paint);

                canvas.drawLine(100,220,300,220,paint);
                canvas.drawArc(50,250,100,400,180,120,true,paint);
                mImageView.setImageBitmap(bitmap);

            }

}