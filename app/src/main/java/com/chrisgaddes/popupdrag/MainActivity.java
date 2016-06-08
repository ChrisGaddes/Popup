package com.chrisgaddes.popupdrag;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.content.Intent;

public class MainActivity extends AppCompatActivity { // implements View.OnTouchListener {

    private static final String TAG = "MainActivity";

//    void initialization(){
//        Button btn_1,
//        final Button b = (Button) findViewById(R.id.btn_pt_1);
//        final Button b = (Button) findViewById(R.id.btn_pt_2);
//
//    }


    //MyTouchListener touchListener = new MyTouchListener();









    // TODO Learn how to draw in Canvas
    // TODO


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btn_load_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                //startActivity(SecondActivity.newIntent(MainActivity.this));
            }
        });



        final View popupContent = getLayoutInflater().inflate(R.layout.popup_main, null);

        // set dimension of popup window. The xml file is set to match parent
        final int dim_popup_window = 700;
        final PopupWindow window = new PopupWindow(popupContent, dim_popup_window, dim_popup_window);

        //final PopupWindow window = new PopupWindow(popupContent, ViewGroup.LayoutParams.WRAP_CONTENT,
        //ViewGroup.LayoutParams.WRAP_CONTENT);

        // b is the name of the button that

        final Button b = (Button) findViewById(R.id.btn_pt_1);

        if (b == null) return;
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        OnPtBtnActionDown(v, b, dim_popup_window, window);
                        break;
                    case MotionEvent.ACTION_UP:

                        //TODO Set it up so that the buttons increment btn_[layout#]_[button#]
                        //TODO move this out of onCreate and make it dynamic so that it doesn't matter how many buttons there are http://stackoverflow.com/questions/7048470/easy-way-to-setonclicklistener-on-all-activity-buttons

                        // TODO http://stackoverflow.com/questions/21872464/get-button-coordinates-and-detect-if-finger-is-over-them-android

                        //TODO put these all in an array

                        // Initialize popup buttons

                        Button b1 = (Button) popupContent.findViewById(R.id.btn_1);
                        Button b2 = (Button) popupContent.findViewById(R.id.btn_2);
                        Button b3 = (Button) popupContent.findViewById(R.id.btn_3);
                        Button b4 = (Button) popupContent.findViewById(R.id.btn_4);
                        Button b5 = (Button) popupContent.findViewById(R.id.btn_moment);

                        // getLocationOnScreen computes the coordinates of the buttons

                        int[] b1Location = new int[2];
                        b1.getLocationOnScreen(b1Location);
                        Rect b1Rect = new Rect(b1Location[0], b1Location[1],
                                b1Location[0] + b1.getWidth(), b1Location[1] + b1.getHeight());


                        //int[] center_b1Rect = new int[]{b1Rect.centerX(), b1Rect.centerY()};

                        // TODO COnsolidate this so that it assigns new variables


                        // TODO instead of reading button pressed, it it will read from a database and Rect will be set to a default size

                        // TODO use canvas to draw arrows at correct places when pressed

                        int[] b2Location = new int[2];
                        b2.getLocationOnScreen(b2Location);
                        Rect b2Rect = new Rect(b2Location[0], b2Location[1],
                                b2Location[0] + b2.getWidth(), b2Location[1] + b2.getHeight());

                        int[] b3Location = new int[2];
                        b3.getLocationOnScreen(b3Location);
                        Rect b3Rect = new Rect(b3Location[0], b3Location[1],
                                b3Location[0] + b3.getWidth(), b3Location[1] + b3.getHeight());

                        int[] b4Location = new int[2];
                        b4.getLocationOnScreen(b4Location);
                        Rect b4Rect = new Rect(b4Location[0], b4Location[1],
                                b4Location[0] + b4.getWidth(), b4Location[1] + b4.getHeight());

                        int[] b5Location = new int[2];
                        b5.getLocationOnScreen(b5Location);
                        Rect b5Rect = new Rect(b5Location[0], b5Location[1],
                                b5Location[0] + b5.getWidth(), b5Location[1] + b5.getHeight());

                        if (b1Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button1");
                            // Toast.makeText(MainActivity.this, "Released on Button1", Toast.LENGTH_SHORT).show();
                            Snackbar.make(v, "Released on Button 1", Snackbar.LENGTH_SHORT).show();

                        } else if (b2Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button2");
                            Snackbar.make(v, "Released on Button 2", Snackbar.LENGTH_SHORT).show();

                        } else if (b3Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button3");
                            Snackbar.make(v, "Released on Button 3", Snackbar.LENGTH_SHORT).show();

                        } else if (b4Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button4");
                            Snackbar.make(v, "Released on Button 4", Snackbar.LENGTH_SHORT).show();

                        } else if (b5Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Moment");
                            Snackbar.make(v, "Released on Moment", Snackbar.LENGTH_SHORT).show();

                        } else {
                            Log.d("MainActivity", "Didn't drag to a button");
                            Snackbar.make(v, "Tap and hold to select button", Snackbar.LENGTH_LONG).show();
                        }

                        window.dismiss();
                        break;
                }
                return v.onTouchEvent(event);
            }
        });
    }

    private void OnPtBtnActionDown(View v, Button b, int dim_popup_window, PopupWindow window) {
        int[] bLoc = new int[2];
        b.getLocationOnScreen(bLoc);
        Rect bRect = new Rect(bLoc[0], bLoc[1],
                bLoc[0] + b.getWidth(), bLoc[1] + b.getHeight());
        int center_bRect_x = bRect.centerX();
        int center_bRect_y = bRect.centerY();

        int offset_center_bRect_x = center_bRect_x - dim_popup_window / 2;
        int offset_center_bRect_y = center_bRect_y - dim_popup_window / 2;

        Log.d(TAG, "Center of button, x:" + offset_center_bRect_x);
        Log.d(TAG, "Center of button, y:" + offset_center_bRect_y);
        window.showAtLocation(v, Gravity.NO_GRAVITY, offset_center_bRect_x, offset_center_bRect_y);
    }
}
