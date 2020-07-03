package com.example.gctest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class LeakActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LeakActivity.class);
    }

    private static final String TAG = "GcTest.Matrix";
    private Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate begin");
        super.onCreate(savedInstanceState);
        doWork();
        Log.i(TAG, "onCreate end");
    }

    public void doWork() {
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 2000000000; i++) {
            count++;
        }
        long end = System.currentTimeMillis();
        Log.i(TAG, "count:" + count);
        Log.i(TAG, "time cost:" + (end - start));
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription =
                Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long value) {
                        //Log.d(TAG, "Got value: " + value);
                        Log.d("GcTest", "Got value: " + value);

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //subscription.unsubscribe();
    }
}
