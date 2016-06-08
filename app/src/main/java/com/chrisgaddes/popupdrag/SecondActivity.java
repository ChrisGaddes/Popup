package com.chrisgaddes.popupdrag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cagad on 6/7/2016.
 */
public class SecondActivity extends AppCompatActivity {


//    private void setMargins (View view, int left, int top, int right, int bottom) {
//        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//            p.setMargins(left, top, right, bottom);
//            view.requestLayout();
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        params.setMargins(10,30,40,50);
//
//
//
//        //for(int i=0;i<30;i++) {
//        int i = 1;
//
//            Button myButton = new Button(this);
//            myButton.setId(i);
//           RelativeLayout.addView(myButton, lp);
//
//
//            relativeLayout.addView(button);
//
//            //this.setContentView(linearLayout, new LinearLayout.LayoutParams(
//            //       LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
//
//       // }
    }
}

