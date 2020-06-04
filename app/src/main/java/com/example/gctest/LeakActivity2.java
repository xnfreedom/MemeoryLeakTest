package com.example.gctest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.util.Log;
import com.example.gctest.DynamicConfigImplDemo;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.trace.TracePlugin;

import java.lang.reflect.Method;


public class LeakActivity2 extends AppCompatActivity implements SensorEventListener{
    public static Application mApplication;
    public static Intent newIntent(Context context) {
        return new Intent(context, LeakActivity2.class);
    }
    public static SensorManager sensorManager;
    void registerListener() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    void calculate() {
        long b = 0;
        for (int i = 0; i < 1000000000; i++ ){
            b += i;
        }
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculate();
            registerListener();

        /*DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        Matrix.Builder builder = new Matrix.Builder(this.getApplication());
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        //builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

        Matrix.init(builder.build());
        //start only startup tracer, close other tracer.
        tracePlugin.start();*/

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
