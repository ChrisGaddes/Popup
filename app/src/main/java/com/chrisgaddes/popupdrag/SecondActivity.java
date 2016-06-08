package com.chrisgaddes.popupdrag;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by cagad on 6/7/2016.
 */
public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    ImageView drawingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        drawingImageView = (ImageView) this.findViewById(R.id.DrawingImageView);
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        // Path

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        Path p = new Path();
        p.moveTo(20, 20);
        p.lineTo(100, 200);
        p.lineTo(200, 100);
        p.lineTo(240, 155);
        p.lineTo(250, 175);
        p.lineTo(20, 20);
        canvas.drawPath(p, paint);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.d(TAG, "Height is: " + height);
        Log.d(TAG, "Width is: " + width);

        Snackbar.make(findViewById(R.id.lyt_linearlayout_second_activity), "Height is" + width, Snackbar.LENGTH_SHORT).show();

        // Height is 2392
        // width is 1440

        //TODO figure out percentage stuff screen

        double x = 0.80;
        double y = 0.60;


        int percentageX= (int)((x*100*100)/width);
        int percentageY= (int)((y*100*100)/height);

        Log.d(TAG, "Percentage X is: " + (double) percentageX);
        Log.d(TAG, "Percentage Y is: " + (double) percentageY);

    }


}

