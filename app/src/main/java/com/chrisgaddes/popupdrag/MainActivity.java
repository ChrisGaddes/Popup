package com.chrisgaddes.popupdrag;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View popupContent = getLayoutInflater().inflate(R.layout.popup_main, null);

        // set dimension of popup window. The xml file is set to match parent
        final int dim_popup_window = 700;
        final PopupWindow window = new PopupWindow(popupContent, dim_popup_window, dim_popup_window);

        //final PopupWindow window = new PopupWindow(popupContent, ViewGroup.LayoutParams.WRAP_CONTENT,
        //ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button b = (Button) findViewById(R.id.btn_pt_1);

        if (b == null) return;
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
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
                        break;
                    case MotionEvent.ACTION_UP:
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
                            Toast.makeText(MainActivity.this, "Released on Button1", Toast.LENGTH_SHORT).show();
                        } else if (b2Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button2");
                            Toast.makeText(MainActivity.this, "Released on Button2", Toast.LENGTH_SHORT).show();
                        } else if (b3Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button3");
                            Toast.makeText(MainActivity.this, "Released on Button3", Toast.LENGTH_SHORT).show();
                        } else if (b4Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button4");
                            Toast.makeText(MainActivity.this, "Released on Button4", Toast.LENGTH_SHORT).show();
                        } else if (b5Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Moment");
                            Toast.makeText(MainActivity.this, "Released on Moment", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("MainActivity", "Didn't drag to a button");
                            Toast.makeText(MainActivity.this, "Tap and hold to select button", Toast.LENGTH_SHORT).show();
                        }

                        window.dismiss();
                        break;
                }
                return v.onTouchEvent(event);
            }
        });
    }
}
