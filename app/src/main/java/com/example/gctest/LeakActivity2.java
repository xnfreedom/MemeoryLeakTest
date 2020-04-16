package com.example.gctest;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;

public class LeakActivity2 extends AppCompatActivity implements SensorEventListener{
    public static Intent newIntent(Context context) {
        return new Intent(context, LeakActivity2.class);
    }
    public static SensorManager sensorManager;
    void registerListener() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //subscription.unsubscribe();
    }


    @Override
    public void onSensorChanged(SensorEvent event){

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

}
