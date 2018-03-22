package com.example.skin.SkinCore;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 *
 * @author tys
 * @date 2018/3/17
 */

public class SkinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ResPluginImpl.getsInstance().init(this);
        ResPluginImpl.getsInstance().load(
                Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                "com.example.tys.pluginapk");
    }
}
