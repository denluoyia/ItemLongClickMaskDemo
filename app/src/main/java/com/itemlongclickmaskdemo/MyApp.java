package com.itemlongclickmaskdemo;

import android.app.Application;

/**
 * Created by denluoyia
 * Date 2018/06/26
 */
public class MyApp extends Application {

    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApp getMyApp() {
        return myApp;
    }
}
