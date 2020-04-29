package com.example.gctest;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.example.gctest.DynamicConfigImplDemo;
import com.tencent.matrix.util.MatrixLog;
import com.example.gctest.MatrixEnum;
import com.tencent.matrix.plugin.Plugin;

public class App extends Application{
    private static final String TAG = "Matrix.Application";
    @Override
    public void onCreate() {
        super.onCreate();
        //resource
        Intent intent = new Intent();
        MatrixLog.i(TAG, "MatrixApplication.onCreate");
        Matrix.Builder builder = new Matrix.Builder(this);
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        //ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.AUTO_DUMP;
        ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.AUTO_DUMP;
        MatrixLog.i(TAG, "Dump Activity Leak Mode=%s", mode);
        intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
        ResourceConfig resourceConfig = new ResourceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .setAutoDumpHprofMode(mode)
//                .setDetectDebuger(true) //matrix test code
                .setNotificationContentIntent(intent)
                .build();
        builder.plugin(new ResourcePlugin(resourceConfig));
        ResourcePlugin.activityLeakFixer(this);
        Matrix.init(builder.build());
        Plugin plugin = Matrix.with().getPluginByClass(ResourcePlugin.class);
        if (!plugin.isPluginStarted()) {
            MatrixLog.i("matrix", "plugin-resource start");
            plugin.start();
        }
    }
    

}
