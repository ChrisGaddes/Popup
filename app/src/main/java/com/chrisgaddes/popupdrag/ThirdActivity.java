package com.chrisgaddes.popupdrag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    //TODO add pinch to zoom http://stackoverflow.com/questions/30979647/how-to-draw-by-finger-on-canvas-after-pinch-to-zoom-coordinates-changed-in-andro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrazyEightsView ev = new CrazyEightsView(this);

        setContentView(ev);


    }

    public class CrazyEightsView extends View {

        // initialize variables
        private Paint blackPaint;
        private Paint paint_arrow;
        private Path path_arrow;

        private int loc_arrow_point_x;
        private int loc_arrow_point_y;
        private int btn_loc_x;
        private int btn_loc_y;

        private double len_arrow_shaft;
        private double len_arrow_head;
        private double angle;
        private double angle_arrow_head_left;
        private double angle_arrow_head_right;
        private float loc_arrow_head_left_x;
        private float loc_arrow_head_left_y;
        private float loc_arrow_head_right_x;
        private float loc_arrow_head_right_y;




        double pi = Math.PI;
        double angles[] = {-pi, -3 * pi / 4, -pi / 2, -pi / 4, 0, pi / 4, pi / 2, 3 * pi / 4, pi, 2 * pi};

        ArrayList<Path> pathList = new ArrayList<Path>();


        public CrazyEightsView(Context context) {
            super(context);

            paint_arrow = new Paint();

            path_arrow = new Path();

            blackPaint = new Paint();
            btn_loc_x = 700;
            btn_loc_y = 700;
            loc_arrow_point_x = btn_loc_x;
            loc_arrow_point_y = btn_loc_y;
            loc_arrow_head_left_x = btn_loc_x;
            loc_arrow_head_left_y = btn_loc_y;
            loc_arrow_head_right_x = btn_loc_x;
            loc_arrow_head_right_y = btn_loc_y;
            len_arrow_shaft = 250;
            len_arrow_head = 80;

            paint_arrow.setStyle(Paint.Style.FILL);
            paint_arrow.setStrokeWidth(15f);
            paint_arrow.setPathEffect(new CornerPathEffect(10));
            paint_arrow.setColor(Color.RED);
            paint_arrow.setStyle(Paint.Style.STROKE);
            //TODO set beginning of shaft to transparent so arrow appears to be at surface
        }

        @Override

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // draws black circle
            canvas.drawCircle(btn_loc_x, btn_loc_y, 50, blackPaint);

            // draws arrow
            for (Path path : pathList) {
                canvas.drawPath(path, paint_arrow);
            }

            //canvas.drawPath(path_arrow, paint_arrow);
        }

        public boolean onTouchEvent(MotionEvent event) {
            int eventaction = event.getAction();
            int X = (int) event.getX();
            int Y = (int) event.getY();

            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:

                    path_arrow = new Path();
                    pathList.add(path_arrow); // <-- Add this line.


                    path_arrow.reset();

                    loc_arrow_point_x = X;
                    loc_arrow_point_y = Y;

                    angle = Math.atan2(loc_arrow_point_y - btn_loc_y, loc_arrow_point_x - btn_loc_x);
                    angle_arrow_head_left = 4 * pi / 3 - angle;
                    angle_arrow_head_right = -pi / 3 - angle;

                    loc_arrow_head_left_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_left) + loc_arrow_point_x);
                    loc_arrow_head_left_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_left) + loc_arrow_point_y);

                    loc_arrow_head_right_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_right) + loc_arrow_point_x);
                    loc_arrow_head_right_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_right) + loc_arrow_point_y);


                    // draws arrow shaft
                    path_arrow.moveTo(btn_loc_x, btn_loc_y);
                    path_arrow.lineTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);

                    // draws arrow head, left and right side
                    path_arrow.lineTo(loc_arrow_head_left_x, loc_arrow_head_left_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.lineTo(loc_arrow_head_right_x, loc_arrow_head_right_y);

                    path_arrow.close();

                    invalidate();// call invalidate to refresh the draw

                    break;
                case MotionEvent.ACTION_MOVE:
                    path_arrow.reset();
                    loc_arrow_point_x = X;
                    loc_arrow_point_y = Y;

                    angle = Math.atan2(loc_arrow_point_y - btn_loc_y, loc_arrow_point_x - btn_loc_x);
                    angle_arrow_head_left = 4 * pi / 3 - angle;
                    angle_arrow_head_right = -pi / 3 - angle;

                    loc_arrow_head_left_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_left) + loc_arrow_point_x);
                    loc_arrow_head_left_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_left) + loc_arrow_point_y);

                    loc_arrow_head_right_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_right) + loc_arrow_point_x);
                    loc_arrow_head_right_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_right) + loc_arrow_point_y);


                    // draws arrow shaft
                    path_arrow.moveTo(btn_loc_x, btn_loc_y);
                    path_arrow.lineTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);

                    // draws arrow head, left and right side
                    path_arrow.lineTo(loc_arrow_head_left_x, loc_arrow_head_left_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.lineTo(loc_arrow_head_right_x, loc_arrow_head_right_y);

                    path_arrow.close();

                    invalidate();// call invalidate to refresh the draw
                    break;
                case MotionEvent.ACTION_UP:
                    // snap to another position

                    path_arrow.reset();
                    loc_arrow_point_x = X;
                    loc_arrow_point_y = Y;
                    angle = Math.atan2(loc_arrow_point_y - btn_loc_y, loc_arrow_point_x - btn_loc_x);


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

                    loc_arrow_point_y = (int) (len_arrow_shaft * Math.sin(angle) + btn_loc_x);
                    loc_arrow_point_x = (int) (len_arrow_shaft * Math.cos(angle) + btn_loc_y);

                    angle_arrow_head_left = 4 * pi / 3 - angle;
                    angle_arrow_head_right = -pi / 3 - angle;

                    loc_arrow_head_left_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_left) + loc_arrow_point_x);
                    loc_arrow_head_left_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_left) + loc_arrow_point_y);
                    loc_arrow_head_right_x = (float) ((float) len_arrow_head * Math.sin(angle_arrow_head_right) + loc_arrow_point_x);
                    loc_arrow_head_right_y = (float) ((float) len_arrow_head * Math.cos(angle_arrow_head_right) + loc_arrow_point_y);


                    // draws arrow shaft
                    path_arrow.moveTo(btn_loc_x, btn_loc_y);
                    path_arrow.lineTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);

                    // draws arrow head, left and right side
                    path_arrow.lineTo(loc_arrow_head_left_x, loc_arrow_head_left_y);
                    path_arrow.moveTo(loc_arrow_point_x, loc_arrow_point_y);
                    path_arrow.lineTo(loc_arrow_head_right_x, loc_arrow_head_right_y);

                    path_arrow.close();

                    invalidate();

                    break;

            }
            return true;
        }
    }
}