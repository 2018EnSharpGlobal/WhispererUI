package com.example.gjwls.whisperer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ListenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listening);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.listen_image).bringToFront();
        fadeInOutAnimation();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ListenActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            // 권한 없음
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String tel = "tel:01030612019";
        startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
//        Intent intent = new Intent(ListenActivity.this,NavigationActivity.class);
//        this.finish();
//        startActivity(intent);
        return true;
    }

    @Override
    protected void onResume(){ super.onResume(); }

    @Override
    protected void onPause(){ super.onPause(); }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void fadeInOutAnimation(){

        final ImageView iv = findViewById(R.id.fade_inout2);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setRepeatCount(Animation.INFINITE);
        fadeIn.setRepeatMode(Animation.REVERSE);
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setRepeatCount(Animation.INFINITE);
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(Animation.INFINITE);

        iv.setAnimation(animation);
    }
}
