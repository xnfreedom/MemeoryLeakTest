package com.example.gctest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    /*用来存放对象的List*/
    static List<Object> list = new ArrayList<>();

    /*用来创建软引用的类*/
    static class SoftObj {
        public SoftObj() {
            Log.d(TAG, "SoftObj(" + this + ") inited");
        }

        @Override
        public void finalize() {
            Log.d(TAG, "SoftObj(" + this + ") finalize");
        }
    }

    /*用来创建弱引用的类*/
    static class WeakObj {
        public WeakObj() {
            Log.d(TAG, "WeakObj(" + this + ") inited");
        }

        @Override
        public void finalize() {
            Log.d(TAG, "WeakObj(" + this + ") finalize");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkDebug(this.getApplicationContext());
        getApplications();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.button_leak).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(LeakActivity.newIntent(MainActivity.this));
            }
        });
        findViewById(R.id.button_leak2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(LeakActivity2.newIntent(MainActivity.this));
            }
        });
        /*创建 6 个软引用对象*/
        list.add(new SoftReference<Object>(new SoftObj()));
        list.add(new SoftReference<Object>(new SoftObj()));
        list.add(new SoftReference<Object>(new SoftObj()));
        list.add(new SoftReference<Object>(new SoftObj()));
        list.add(new SoftReference<Object>(new SoftObj()));
        list.add(new SoftReference<Object>(new SoftObj()));

        /*创建 6 个弱引用对象*/
        list.add(new WeakReference<Object>(new WeakObj()));
        list.add(new WeakReference<Object>(new WeakObj()));
        list.add(new WeakReference<Object>(new WeakObj()));
        list.add(new WeakReference<Object>(new WeakObj()));
        list.add(new WeakReference<Object>(new WeakObj()));
        list.add(new WeakReference<Object>(new WeakObj()));


    }

    public void getPackages() {
        int flag = PackageManager.MATCH_UNINSTALLED_PACKAGES;
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(flag);
        for (PackageInfo installedPackage : installedPackages) {
            Log.d(TAG, "packagename = " + installedPackage.packageName);
        }
    }

    public void getApplications() {
        int flag = PackageManager.MATCH_UNINSTALLED_PACKAGES;
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> listApplications = packageManager.getInstalledApplications(flag);
        Collections.sort(listApplications, new ApplicationInfo.DisplayNameComparator(packageManager));
        for (ApplicationInfo info : listApplications) {
            Log.i(TAG, "packagename = " + info.packageName);
        }
    }

    public void checkDebug(Context context){
        int flags = context.getApplicationInfo().flags;
        Log.d("lxy", "flags: " + flags);
        Log.d("lxy", "ApplicationInfo.FLAG_DEBUGGABLE: " + ApplicationInfo.FLAG_DEBUGGABLE);
        boolean result = (flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.d("lxy", "result: " + result);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
