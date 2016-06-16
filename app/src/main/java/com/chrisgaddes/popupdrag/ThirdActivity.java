package com.chrisgaddes.popupdrag;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    //TODO add pinch to zoom http://stackoverflow.com/questions/30979647/how-to-draw-by-finger-on-canvas-after-pinch-to-zoom-coordinates-changed-in-andro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        DrawArrowsView ev = new DrawArrowsView(this);

        setContentView(ev);

//        Thread myThread = new Thread(myRunnable);
//        myThread.start();
    }

    public class DrawArrowsView extends View {

        // initialize variables
        private Paint paint_points;
        private Paint paint_arrow;
        private Path path_arrow;

        private int loc_arrow_point_x;
        private int loc_arrow_point_y;
        private int btn_loc_x;
        private int btn_loc_y;

        private double len_arrow_shaft;
        private double len_arrow_shaft_start;
        private double len_arrow_head;
        private double len_arrow_shaft_current;

        private float dim_btn_radius;
        private long time_anim_arrow_dur;

        private double angle;
        private double angle_dif;
        private double tmp_angle_dist;
        private double angle_dist;
        private double arrow_animated_fraction;

        private double angle_arrow_head_left;
        private double angle_arrow_head_right;
        private float loc_arrow_head_left_x;
        private float loc_arrow_head_left_y;
        private float loc_arrow_head_right_x;
        private float loc_arrow_head_right_y;

        double pi = Math.PI;

        // angles the force arrows snap to
        double angles[] = {-pi, -3 * pi / 4, -pi / 2, -pi / 4, 0, pi / 4, pi / 2, 3 * pi / 4, pi, 2 * pi};

        // initialize ArrayLists for paths and points
        ArrayList<Point> pointList = new ArrayList<>();
        ArrayList<Path> pathList = new ArrayList<>();

        public DrawArrowsView(Context context) {
            super(context);

            paint_arrow = new Paint();
            path_arrow = new Path();

            dim_btn_radius = 30f;
            time_anim_arrow_dur = 200;

            Point pointOne = new Point(275, 700);
            Point pointTwo = new Point(730, 700);
            Point pointThree = new Point(1150, 700);
            pointList.add(pointOne);
            pointList.add(pointTwo);
            pointList.add(pointThree);

            paint_points = new Paint();
            btn_loc_x = pointList.get(1).x;
            btn_loc_y = pointList.get(1).y;
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

            // draws black circle at points in ArrayList pointList
            for (Point ptLst_dots : pointList) {
                canvas.drawCircle(ptLst_dots.x, ptLst_dots.y, dim_btn_radius, paint_points);
            }

            // draws arrows
            for (Path pthLst_arrows : pathList) {
                canvas.drawPath(pthLst_arrows, paint_arrow);
            }
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
                    drawArrow();
                    invalidate();// call invalidate to refresh the draw
                    break;

                case MotionEvent.ACTION_MOVE:
                    path_arrow.reset();
                    loc_arrow_point_x = X;
                    loc_arrow_point_y = Y;
                    angle = Math.atan2(loc_arrow_point_y - btn_loc_y, loc_arrow_point_x - btn_loc_x);
                    drawArrow();
                    invalidate();// call invalidate to refresh the draw
                    break;

                case MotionEvent.ACTION_UP:
                    path_arrow.reset();
                    loc_arrow_point_x = X;
                    loc_arrow_point_y = Y;

                    // calculates the angle of arrow at release
                    final double angle_start = Math.atan2(loc_arrow_point_y - btn_loc_y, loc_arrow_point_x - btn_loc_x);

                    // snaps arrow to pi/4 increments
                    angle_dist = Math.abs(angles[0] - angle_start);
                    int idx = 0;
                    for (int c = 1; c < angles.length; c++) {
                        tmp_angle_dist = Math.abs(angles[c] - angle_start);
                        if (tmp_angle_dist < angle_dist) {
                            idx = c;
                            angle_dist = tmp_angle_dist; //
                        }
                    }
                    angle_dif = angles[idx] - angle_start;

                    len_arrow_shaft_start = Math.hypot((loc_arrow_point_x - btn_loc_x), (loc_arrow_point_y - btn_loc_y));

                    ValueAnimator animator = ValueAnimator.ofFloat((float) len_arrow_shaft_start, (float) len_arrow_shaft);
                    animator.setDuration(time_anim_arrow_dur);
                    animator.setInterpolator(new OvershootInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            arrow_animated_fraction = animation.getAnimatedFraction();
                            len_arrow_shaft_current = (Float) animation.getAnimatedValue();
                            angle = angle_start + arrow_animated_fraction * angle_dif;
                            loc_arrow_point_y = (int) (len_arrow_shaft_current * Math.sin(angle) + btn_loc_y);
                            loc_arrow_point_x = (int) (len_arrow_shaft_current * Math.cos(angle) + btn_loc_x);
                            path_arrow.reset();
                            drawArrow();
                            invalidate();
                        }
                    });
                    animator.start();

                    double angle_degrees = Math.toDegrees(-angle);
                    Snackbar.make(this, "Created force at " + angle_degrees, Snackbar.LENGTH_SHORT).show();

                    break;
            }
            return true;
        }

        private void drawArrow() {
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
        }
    }

//
//    Runnable myRunnable = new Runnable() {
//        @Override
//        public void run() {
//            while (len_arrow_shaft_current > len_arrow_shaft) {
//                Thread.sleep(1000); // Waits for 1 second (1000 milliseconds)
////                String updateWords = updateAuto(); // make updateAuto() return a string
//                loc_arrow_point_y = (int) (len_arrow_shaft * Math.sin(angle3) + btn_loc_y);
//                loc_arrow_point_x = (int) (len_arrow_shaft * Math.cos(angle3) + btn_loc_x);
//
//                drawArrow();
//                invalidate();
//
//                myTextView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        myTextView.setText(updateWords);
//                    }
//
//                });
//            }
//        }
//
//        ;
//
//    };
}