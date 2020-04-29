package com.example.gctest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.plugin.Plugin;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.matrix.util.MatrixUtil;

public class LeakActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, LeakActivity.class);
    }

    private static final String TAG = LeakActivity.class.getSimpleName();
    private Subscription subscription;
    @Override protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();

        subscription =
                Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                    @Override public void call(Long value) {
                        Log.d(TAG, "Got value: " + value);
                    }
                });
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        //subscription.unsubscribe();
    }
}
