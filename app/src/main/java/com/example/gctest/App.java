package com.example.gctest;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import android.util.Log;
public class App extends Application{
    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mRefWatcher = LeakCanary.install(this);
        Log.d("leak", "mRefWatcher:" + mRefWatcher.toString());
    }
    
    public static RefWatcher getRefWatcher() {
        Log.d("leak", "getRefWatcher:" + mRefWatcher);
        return mRefWatcher;
    }
}
