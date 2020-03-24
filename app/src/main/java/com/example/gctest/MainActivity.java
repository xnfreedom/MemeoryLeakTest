package com.example.gctest;

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
