package com.chrisgaddes.popupdrag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrowView ev = new ArrowView(this);

        setContentView(ev);
    }

    public class ArrowView extends View {

        private Paint redPaint;
        private Paint blackPaint;
        private int circleX;
        private int circleY;
        private int btn_loc_x;
        private int btn_loc_y;
        private double length_arrow;
        private double angle;

        private double len_arrow_head;
        private float x3;
        float x4;
        float y3;
        float y4;

        private double arrow_head_left;
        private double arrow_head_right;
        float loc_arrow_head_left_x;
        float loc_arrow_head_left_y;
        float loc_arrow_head_right_x;
        float loc_arrow_head_right_y;


        double pi = Math.PI;
        double angles[] = {-pi, -3 * pi / 4, -pi / 2, -pi / 4, 0, pi / 4, pi / 2, 3 * pi / 4, pi, 2 * pi};

        public ArrowView(Context context) {
            super(context);
            redPaint = new Paint();
            redPaint.setAntiAlias(true);
            redPaint.setColor(Color.rgb(230, 00, 00));
            redPaint.setStrokeWidth(10f);

            blackPaint = new Paint();

            btn_loc_x = 700;
            btn_loc_y = 700;
            circleX = btn_loc_x;
            circleY = btn_loc_y;
            loc_arrow_head_left_x = btn_loc_x;
            loc_arrow_head_left_y = btn_loc_y;
            loc_arrow_head_right_x = btn_loc_x;
            loc_arrow_head_right_y = btn_loc_y;
            length_arrow = 300;
            len_arrow_head = 100;

//http://stackoverflow.com/questions/16559244/rectangle-is-not-drawn-at-its-original-coordinates


        }

        @Override

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            Paint paint_green_line = new Paint();
            Path path = new Path();
            paint_green_line.setStyle(Paint.Style.FILL);
            paint_green_line.setColor(Color.TRANSPARENT);
            canvas.drawPaint(paint_green_line);
            path.moveTo(btn_loc_x, btn_loc_y);
            path.lineTo(circleX, circleY);


            path.close();
            paint_green_line.setStrokeWidth(20f);
            paint_green_line.setPathEffect(null);
            paint_green_line.setColor(Color.GREEN);
            paint_green_line.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint_green_line);

            Paint paint_arrow_head = new Paint();
            Path path_arrow_head = new Path();
            paint_arrow_head.setStyle(Paint.Style.FILL);
            paint_arrow_head.setColor(Color.TRANSPARENT);
            canvas.drawPaint(paint_arrow_head);

            path_arrow_head.moveTo(circleX, circleY);
            path_arrow_head.lineTo(loc_arrow_head_left_x, loc_arrow_head_left_y);

            path_arrow_head.moveTo(circleX, circleY);
            path_arrow_head.lineTo(loc_arrow_head_right_x, loc_arrow_head_right_y);

            path_arrow_head.close();
            paint_arrow_head.setStrokeWidth(20f);
            paint_arrow_head.setPathEffect(null);
            paint_arrow_head.setColor(Color.GREEN);
            paint_arrow_head.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path_arrow_head, paint_arrow_head);


            canvas.drawCircle(btn_loc_x, btn_loc_y, 50, blackPaint);
            //canvas.drawLine(btn_loc_x, btn_loc_y, circleX, circleY, redPaint);

//            canvas.drawLine(btn_loc_x, btn_loc_y, circleX, circleY, paint_arrow_head);
//            canvas.drawLine(circleX, circleY, x3, y3, paint_arrow_head);
//            canvas.drawLine(circleX, circleY, x4, y4, paint_arrow_head);

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

                    angle = Math.atan2(circleY - btn_loc_y, circleX - btn_loc_x);
                    arrow_head_left = 4 * pi / 3 - angle;
                    arrow_head_right = -pi / 3 - angle;

                    loc_arrow_head_left_x = (float) ((float) len_arrow_head * Math.sin(arrow_head_left) + circleX);
                    loc_arrow_head_left_y = (float) ((float) len_arrow_head * Math.cos(arrow_head_left) + circleY);

                    loc_arrow_head_right_x = (float) ((float) len_arrow_head * Math.sin(arrow_head_right) + circleX);
                    loc_arrow_head_right_y = (float) ((float) len_arrow_head * Math.cos(arrow_head_right) + circleY);

                    invalidate();// call invalidate to refresh the draw
                    break;
                case MotionEvent.ACTION_UP:
                    // snap to another position
                    circleX = X;
                    circleY = Y;
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

                    double angle_degrees = Math.toDegrees(-angle);
                    Snackbar.make(this, "Created force at " + angle_degrees, Snackbar.LENGTH_SHORT).show();

                    circleY = (int) (length_arrow * Math.sin(angle) + btn_loc_x);
                    circleX = (int) (length_arrow * Math.cos(angle) + btn_loc_y);

                    arrow_head_left = 4 * pi / 3 - angle;
                    arrow_head_right = -pi / 3 - angle;

                    loc_arrow_head_left_x = (float) ((float) len_arrow_head * Math.sin(arrow_head_left) + circleX);
                    loc_arrow_head_left_y = (float) ((float) len_arrow_head * Math.cos(arrow_head_left) + circleY);
                    loc_arrow_head_right_x = (float) ((float) len_arrow_head * Math.sin(arrow_head_right) + circleX);
                    loc_arrow_head_right_y = (float) ((float) len_arrow_head * Math.cos(arrow_head_right) + circleY);

                    invalidate();

                    break;

            }
            return true;
        }
    }
}