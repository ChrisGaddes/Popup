package williamhester.me.popupdrag;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View popupContent = getLayoutInflater().inflate(R.layout.popup_main, null);
        final PopupWindow window = new PopupWindow(popupContent, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button b = (Button) findViewById(R.id.button);
        if (b == null) return;
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        window.showAsDropDown(b);
                        break;
                    case MotionEvent.ACTION_UP:
                        Button b1 = (Button) popupContent.findViewById(R.id.button_1);
                        Button b2 = (Button) popupContent.findViewById(R.id.button_2);

                        int[] b1Location = new int[2];
                        b1.getLocationOnScreen(b1Location);
                        Rect b1Rect = new Rect(b1Location[0], b1Location[1],
                                b1Location[0] + b1.getWidth(), b1Location[1] + b1.getHeight());

                        int[] b2Location = new int[2];
                        b2.getLocationOnScreen(b2Location);
                        Rect b2Rect = new Rect(b2Location[0], b2Location[1],
                                b2Location[0] + b2.getWidth(), b2Location[1] + b2.getHeight());

                        if (b1Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button1");
                        } else if (b2Rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            Log.d("MainActivty", "Released on Button2");
                        } else {
                            Log.d("MainActivity", "Didn't drag to a button");
                        }

                        window.dismiss();
                        break;
                }
                return v.onTouchEvent(event);
            }
        });
    }
}
