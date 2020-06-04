package com.example.gctest;

import android.app.Application;
import android.util.Log;
import com.example.gctest.DynamicConfigImplDemo;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.trace.TracePlugin;
import android.os.Debug;
import com.tencent.matrix.trace.core.AppMethodBeat;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("matrixGcTest", "before debug=====");

        Debug.waitForDebugger();
        //AppMethodBeat.init();
        Log.d("matrixGcTest", "begin debug=====");
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        Matrix.Builder builder = new Matrix.Builder(this);
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        Log.d("matrixGcTest", "after Matrix.Builder");
        //builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                //.splashActivities("com.example.gctest.MainActivity")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);
        Log.d("matrixGcTest", "after builder.plugin");

        Matrix.init(builder.build());
        Log.d("matrixGcTest", "after Matrix.init");
        //start only startup tracer, close other tracer.
        tracePlugin.start();




    }
    

}
