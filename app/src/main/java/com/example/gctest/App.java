package com.example.gctest;

import android.app.Application;
import android.util.Log;
import com.example.gctest.DynamicConfigImplDemo;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.trace.TracePlugin;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        Matrix.Builder builder = new Matrix.Builder(this);
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
        tracePlugin.start();

    }
    

}
