package com.example.skin.SkinCore;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;


import com.example.skin.SkinConfig.ResChangeView;
import com.example.skin.SkinConfig.TypeFaceChangeView;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author tys
 * @date 2018/3/17
 */

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    /**
     * 记录属性变化的view
     */
    HashMap<String, ResChangeView> cacheMap = new HashMap<>();

    /**
     * 记录TypeFace变化的TextView
     */
    HashMap<String, TypeFaceChangeView> cacheMapTypeFace = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        installViewFactory(activity);
        ResPluginImpl.getsInstance().upDataStatusBar(activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        cacheMap.remove(activity.getClass().getName());
        cacheMapTypeFace.remove(activity.getClass().getName());
    }


    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }


    private void installViewFactory(Activity context) {
        final LayoutInflater layoutInflater;
        if (context instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            layoutInflater = LayoutInflater.from(appCompatActivity);
            try {
                //delegate.installViewFactory(); 已经完成了(super.)
                Field mFactorySetFiled = LayoutInflater.class.getDeclaredField("mFactorySet");
                mFactorySetFiled.setAccessible(true);
                mFactorySetFiled.setBoolean(layoutInflater, false);
                LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflaterV1(appCompatActivity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Log.e("----", "iii:"+context.getClass().getName());
        }
    }
}
