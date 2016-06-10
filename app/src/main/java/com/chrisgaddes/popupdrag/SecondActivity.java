package com.chrisgaddes.popupdrag;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

public class SecondActivity extends AppCompatActivity {


    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayeredImageView v = new LayeredImageView(this);
        Resources res = v.getResources();

        v.setImageResource(R.drawable.background);

        Matrix m;

        m = new Matrix();
        m.preTranslate(81, 146); // pixels to offset
        final LayeredImageView.Layer layer1 = v.addLayer(res.getDrawable(R.drawable.layer1), m);

        m = new Matrix();
        m.preTranslate(62, 63); // pixels to offset
        final LayeredImageView.Layer layer0 = v.addLayer(0, res.getDrawable(R.drawable.layer0), m);


        final AnimationDrawable ad = new AnimationDrawable();
        ad.setOneShot(false);
        Drawable frame1, frame2;
        frame1 = res.getDrawable(R.drawable.layer0);
        frame2 = res.getDrawable(R.drawable.layer1);
        ad.addFrame(frame1, 3000);
        ad.addFrame(frame2, 1000);
        ad.addFrame(frame1, 250);
        ad.addFrame(frame2, 250);
        ad.addFrame(frame1, 250);
        ad.addFrame(frame2, 250);
        ad.addFrame(frame1, 250);
        ad.addFrame(frame2, 250);
        ad.setBounds(200, 20, 300, 120);
        v.addLayer(1, ad);
        v.post(new Runnable() {
            @Override
            public void run() {
                ad.start();
            }
        });

        int[] colors = {
                0xeeffffff,
                0xee0038a8,
                0xeece1126,
        };
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd.setBounds(0, 0, 100, 129);
        gd.setCornerRadius(20);
        gd.setStroke(5, 0xaa666666);
        final Matrix mm = new Matrix();
        mm.preTranslate(200, 69); // pixels to offset
        mm.preRotate(20, 50, 64.5f);
        final LayeredImageView.Layer layer2 = v.addLayer(2, gd, mm);

        final Animation as = AnimationUtils.loadAnimation(this, R.anim.anim_set);

        final Runnable action1 = new Runnable() {
            @Override
            public void run() {
                Animation a;
                Interpolator i;

                i = new Interpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        return (float) Math.sin(input * Math.PI);
                    }
                };
                as.setInterpolator(i);
                layer0.startLayerAnimation(as);

                a = new TranslateAnimation(0, 0, 0, 100);
                a.setDuration(3000);
                i = new Interpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        float output = (float) Math.sin(Math.pow(input, 2.5f) * 12 * Math.PI);
                        return (1 - input) * output;
                    }
                };
                a.setInterpolator(i);
                layer1.startLayerAnimation(a);

                a = new AlphaAnimation(0, 1);
                i = new Interpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        return (float) (1 - Math.sin(input * Math.PI));
                    }
                };
                a.setInterpolator(i);
                a.setDuration(2000);
                layer2.startLayerAnimation(a);
            }
        };
        View.OnClickListener l1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action1.run();
            }
        };
        v.setOnClickListener(l1);
        v.postDelayed(action1, 2000);

        //    final float[] values = new float[9];
//    final float[] pts = new float[2];
//    final Matrix inverse = new Matrix();;
//    OnTouchListener l = new OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
//            int action = event.getAction();
//            if (action != MotionEvent.ACTION_UP) {
//                if (inverse.isIdentity()) {
//                    v.getImageMatrix().invert(inverse);
//                    Log.d(TAG, "onTouch set inverse");
//                }
//                pts[0] = event.getX();
//                pts[1] = event.getY();
//                inverse.mapPoints(pts);
//
//                mm.getValues(values);
//                // gd's bounds are (0, 0, 100, 129);
//                values[Matrix.MTRANS_X] = pts[0] - 100 / 2;
//                values[Matrix.MTRANS_Y] = pts[1] - 129 / 2;
//                mm.setValues(values);
//                v.invalidate();
//            }
//            return false;
//        }
//    };
//    v.setOnTouchListener(l);
        setContentView(v);

    }
}