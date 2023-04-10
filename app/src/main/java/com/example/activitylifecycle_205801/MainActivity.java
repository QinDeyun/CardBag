package com.example.activitylifecycle_205801;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "LIFECYCLE";

    @Override //完全生命周期开始时被调用，初始化 Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "(1) onCreate()");
    }

    @Override //可视生命周期开始时被调用，对用户界面进行必要的更改
    public void onStart() {
        super.onStart();
        Log.i(TAG, "(2) onStart()");
    }

    @Override // 在 onStart() 后被调用，用于恢复onSaveInstanceState()保存的用户界面信息
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        Log.i(TAG, "(3) onRestoreInstanceState()");
    }

    @Override //在活动生命周期开始时被调用，恢复被 onPause()停止的用于界面更新的资源
    public void onResume() {
        super.onResume();
        Log.i(TAG, "(4) onResume()");
    }

    @Override // 在 onResume()后被调用，保存界面信息
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "(5) onSaveInstanceState()");
    }

    @Override //在重新进入可视生命周期前被调用，载入界面所需要的更改信息
    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "(6) onRestart()");
    }

    @Override //在活动生命周期结束时被调用，用来保存持久的数据或释放占用的资源
    public void onPause() {
        super.onPause();
        Log.i(TAG, "(7) onPause()");
    }

    @Override //在可视生命周期结束时被调用，一般用来保存持久的数据或释放占用的资源
    public void onStop() {
        super.onStop();
        Log.i(TAG, "(8) onStop()");
    }

    @Override //在完全生命周期结束时被调用，释放资源，包括线程、数据连接等
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "(9) onDestroy()");
    }
    

    //https://developer.android.google.cn/studio/debug/am-logcat.html?hl=zh-cn
    //https://zhuanlan.zhihu.com/p/336860671
}