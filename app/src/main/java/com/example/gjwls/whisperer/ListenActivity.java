package com.example.gjwls.whisperer;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.ThemedSpinnerAdapter;

public class ListenActivity extends AppCompatActivity {
    private boolean flag;
    private String mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listening);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.listen_image).bringToFront();
        fadeInOutAnimation();

        mode = getIntent().getStringExtra("mode");
        flag = true;

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ListenActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            // 권한 없음
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(flag) {
            switch (mode){
                case Constants.HELP:
                    ProgressDialog dialog1 = ProgressDialog.show(ListenActivity.this, "",
                            "지하철 정보를 가져오는 중입니다...", true);
                    Intent intent1 = new Intent(ListenActivity.this,HelpActivity.class);
                    this.finish();
                    startActivity(intent1);
                    break;
                case Constants.NAVIGATION:
                    ProgressDialog dialog2 = ProgressDialog.show(ListenActivity.this, "",
                                "목적지를 설정하는 중입니다...", true);
                    Intent intent2 = new Intent(ListenActivity.this,NavigationActivity.class);
                    this.finish();
                    startActivity(intent2);
                    break;
                case Constants.CALL:
                    String tel = "tel:0263117261";
                    this.finish();
                    startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                    break;
            }
            flag = false;
        }

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
