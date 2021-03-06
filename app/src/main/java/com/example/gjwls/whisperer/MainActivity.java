package com.example.gjwls.whisperer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList work_List;
    private GestureDetector.SimpleOnGestureListener listener;
    private GestureDetector detector;
    private int count;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        work_List = new ArrayList();
        findViewById(R.id.main_image).bringToFront();

        listener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(count==0) { mode = Constants.HELP; }
                else if(count==1) { mode = Constants.NAVIGATION; }
                else if(count==2) { mode = Constants.CALL; }
                else { mode = Constants.NAVIGATION; }

                Intent intent = new Intent(MainActivity.this, ListenActivity.class);
                intent.putExtra("mode",mode);
                startActivity(intent);

                count++;
                return true;
            }
        };
        detector = new GestureDetector(listener);
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        findViewById(R.id.fade_inout1).clearAnimation();
    }

}
