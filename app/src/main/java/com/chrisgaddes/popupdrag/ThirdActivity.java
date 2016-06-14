package com.chrisgaddes.popupdrag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrazyEightsView ev = new CrazyEightsView(this);

        setContentView(ev);
    }

    public class CrazyEightsView extends View {

        private Paint redPaint;
        private Paint blackPaint;
        private int circleX;
        private int circleY;
        private int dim_square;
        private int left_coord, top_coord, right_coord, bottom_coord;
        private int btn_loc_x;
        private int btn_loc_y;
        private double length_arrow;
        private Double angle;

        double pi = Math.PI;
        double angles[] = {-pi, -3 * pi / 4, -pi / 2, -pi / 4, 0, pi / 4, pi / 2, 3 * pi / 4, pi, 2 * pi};

        public CrazyEightsView(Context context) {
            super(context);
            redPaint = new Paint();
            redPaint.setAntiAlias(true);
            redPaint.setColor(Color.rgb(230, 00, 00));
            redPaint.setStrokeWidth(10f); //TODO change from absolute to dependant on

            blackPaint = new Paint();


            btn_loc_x = 700;
            btn_loc_y = 700;
            circleX = btn_loc_x;
            circleY = btn_loc_y;


            dim_square = 200;


            left_coord = circleX;// - dim_square / 2;
            top_coord = btn_loc_y - dim_square / 2;
            right_coord = btn_loc_x + dim_square / 2;
            bottom_coord = btn_loc_y + dim_square / 2;
            Rect rectangle = new Rect(left_coord, top_coord, right_coord, bottom_coord);

            Log.d(TAG, "Rectangle Coordinates" + rectangle);

//http://stackoverflow.com/questions/16559244/rectangle-is-not-drawn-at-its-original-coordinates


        }

        @Override

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            Path path = new Path();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.TRANSPARENT);
            canvas.drawPaint(paint);
            path.moveTo(btn_loc_x, btn_loc_y);
            path.lineTo(circleX, circleY);
            path.close();
            paint.setStrokeWidth(20f);
            paint.setPathEffect(null);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);


            canvas.drawCircle(btn_loc_x, btn_loc_y, 50, blackPaint);
            //canvas.drawLine(btn_loc_x, btn_loc_y, circleX, circleY, redPaint);

        }

        public boolean onTouchEvent(MotionEvent event) {
            int eventaction = event.getAction();
            int X = (int) event.getX();
            int Y = (int) event.getY();

            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    circleX = X;
                    circleY = Y;

                    invalidate();// call invalidate to refresh the draw
                    break;
                case MotionEvent.ACTION_UP:
                    // snap to another position
                    circleX = X;
                    circleY = Y;
                    length_arrow = 300;
                    angle = Math.atan2(circleY - btn_loc_y, circleX - btn_loc_x);


                    double distance = Math.abs(angles[0] - angle);
                    int idx = 0;
                    for (int c = 1; c < angles.length; c++) {
                        double cdistance = Math.abs(angles[c] - angle);
                        if (cdistance < distance) {
                            idx = c;
                            distance = cdistance;
                        }
                    }
                    double angle = angles[idx];

                    Log.d(TAG, "Angle snap = " + angle);

                    double angle_degrees = Math.toDegrees(-angle);

                    Snackbar.make(this, "Created force at " + angle_degrees , Snackbar.LENGTH_SHORT).show();

                    circleY = (int) (length_arrow * Math.sin(angle) + btn_loc_x);
                    circleX = (int) (length_arrow * Math.cos(angle) + btn_loc_y);

                    invalidate();


                    break;

            }
            return true;
        }
    }
}