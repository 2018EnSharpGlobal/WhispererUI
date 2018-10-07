package com.example.gjwls.whisperer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.gjwls.whisperer.Navigation.Navigation;

public class NavigationActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private boolean flag = true;
    private double beforeDegree= 0.0;
    private double degree;
    private double latitude=36.0, longitude=128.0;

    private Navigation navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigation = new Navigation();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void startAnimation(double fromDegree,double toDegree){
        final ImageView iv = findViewById(R.id.guideImage);

        final RotateAnimation rotate = new RotateAnimation((float) fromDegree, (float) toDegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(210);
        rotate.setFillAfter(true);
        iv.startAnimation(rotate);
    }

    private void changeDirection(double bearing){

        double mode = 0;

//        if(flag) {
//            degree = 350;//Double.parseDouble(navigation.outPut_Navi(bearing).split(",")[0]);
//            flag = false;
//        }
//        else if(navigation.get_navi_final_node(latitude,longitude, bearing))
//            degree = Double.parseDouble(navigation.outPut_Navi(bearing).split(",")[0]);

        mode = degree-bearing;

        Log.e("sensor",String.valueOf(mode));

        startAnimation(beforeDegree,mode);
        beforeDegree=mode;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        changeDirection(degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
