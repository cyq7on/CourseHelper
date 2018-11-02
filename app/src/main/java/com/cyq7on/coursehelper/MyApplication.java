package com.cyq7on.coursehelper;

import android.app.Application;

import com.orhanobut.logger.Logger;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        Logger.init("smile");
    }

}
