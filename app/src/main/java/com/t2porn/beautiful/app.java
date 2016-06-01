package com.t2porn.beautiful;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by t2porn on 2016/6/1.
 */
public class App extends Application{
    private final String TAG = getClass().getName();

    @Override
    public void onCreate(){
        Log.v("App", "onCreate");
        super.onCreate();
        Fresco.initialize(this); //理论上应该放在application初始化里的
        Log.d(TAG, "初始化");
    }
}
