package com.example.tys.ui;

import android.app.Application;

import com.example.skin.SkinCore.ResPluginImpl;

/**
 *
 * @author tys
 * @date 2018/3/22
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ResPluginImpl.getsInstance().init(this);
    }
}
