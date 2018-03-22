package com.example.skin.SkinCore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.skin.SkinConfig.ChangedView;
import com.example.skin.SkinConfig.ResChangeView;
import com.example.skin.SkinConfig.ResUtil;
import com.example.skin.SkinConfig.SkinConfig;
import com.example.skin.SkinConfig.TypeFaceChangeView;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;


/**
 * @author tys
 * @date 2017/9/23
 */

public class ResPluginImpl implements ResPlugin {
    private static final String TAG = "ResPluginImpl";

    private String pluginApkPath = "";
    private String pluginApkPackageName = "";

    private Resources mResources;
    private Application mAppContext;
    private ActivityLifecycle activityLifecycleCallBack = null;
    private static ResPluginImpl sInstance;
    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static int[] STATUS_BAR_COLOR_ATTRS = {
            android.R.attr.statusBarColor
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static int[] NAVIGATION_BAR_COLOR_ATTRS = {
            android.R.attr.navigationBarColor
    };


    private ResPluginImpl() {
    }

    public static ResPluginImpl getsInstance() {
        if (sInstance == null) {
            synchronized (ResPluginImpl.class) {
                if (sInstance == null) {
                    sInstance = new ResPluginImpl();
                }
            }
        }
        return sInstance;
    }

    public void init(Application app) {
        mAppContext = app;
        if (activityLifecycleCallBack != null) {
            mAppContext.unregisterActivityLifecycleCallbacks(activityLifecycleCallBack);
        }
        activityLifecycleCallBack = new ActivityLifecycle();
        mAppContext.registerActivityLifecycleCallbacks(activityLifecycleCallBack);
    }

    public void load(String pluginFilePath, String pluginApkPackageName) {
        this.pluginApkPath = pluginFilePath;
        this.pluginApkPackageName = pluginApkPackageName;
        loadPluginFile(pluginFilePath);
    }

    @Override
    public void setRes(Activity activity, String suffixName) {
        ResUtil.setResSuffix(activity, suffixName);
        changeRes();
    }

    @Override
    public void setTypeFace(Context context, String typeFaceName) {
        ResUtil.setTypeFaceName(context, typeFaceName);
        changeTypeFace();
    }

    @Override
    public void setStatusBarColor(Activity activity, String nameInPlugin){
        ResUtil.setStatusBarColorName(activity, nameInPlugin);
        changeStatusBar(activity);
    }

    @Override
    public void setNavigateBarColor(Activity activity, String nameInPlugin){
        ResUtil.setNavigateBarColorName(activity, nameInPlugin);
        changeNavigationBar(activity);
    }


    /**
     * 设置Activity的StatusBar 和 NavigationBar
     *
     * @param activity
     */
    void upDataStatusBar(Activity activity) {
        changeStatusBar(activity);
        changeNavigationBar(activity);
    }

    private void changeNavigationBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        SupportAttrType navigationBar = ResUtil.getSupportAttrType("navigationBar");
        if (navigationBar != null) {
            int navigationColorId = 0;
            boolean isFromPlugin = true;
            //没有应用插件资源（还原）
            if (TextUtils.isEmpty(ResUtil.getNavigateBarColorName(activity))) {
                navigationColorId = ResUtil.getResId(activity.getBaseContext(), NAVIGATION_BAR_COLOR_ATTRS)[0];
                isFromPlugin = false;
            }
            navigationBar.apply(isFromPlugin, activity, SkinConfig.NAVIGATION_BAR_COLOR_VAL, navigationColorId);
        }
    }

    private void changeStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        SupportAttrType statusBar = ResUtil.getSupportAttrType("statusBar");
        if (statusBar != null) {
            int primaryColorId = 0;
            boolean isFromPlugin = true;
            //没有应用了插件资源（还原）
            if (TextUtils.isEmpty(ResUtil.getStatusBarColorName(activity))) {
                //如果没有配置属性 则获得0
                primaryColorId = ResUtil.getResId(activity, STATUS_BAR_COLOR_ATTRS)[0];
                if (0 == primaryColorId) {
                    primaryColorId = ResUtil.getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
                }
                isFromPlugin = false;
            }
            statusBar.apply(isFromPlugin, activity, SkinConfig.STATUS_BAR_COLOR_VAL, primaryColorId);
        }
    }

    /**
     * 更改缓存中的所有view的属性
     */
    private void changeRes() {
        for (Map.Entry<String, ResChangeView> val : activityLifecycleCallBack.cacheMap.entrySet()) {
            HashSet<ChangedView> views = val.getValue().views;
            for (ChangedView view : views) {
                changeSkin(view.changedView, view.changedAttr, view.changedVal);
            }
        }
    }

    private void changeTypeFace(){
        Typeface typeface = getTypeface(SkinConfig.SKIN_TYPEFACE_DIR + SkinConfig.SKIN_TYPEFACE_NAME_VAL);
        for (Map.Entry<String, TypeFaceChangeView> val : activityLifecycleCallBack.cacheMapTypeFace.entrySet()) {
            HashSet<View> views = val.getValue().views;
            for (View view : views) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    @Override
    public void loadPluginFile(String pluginApkPath) {
        try {
            AssetManager mAssetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = mAssetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(mAssetManager, pluginApkPath);
            Resources hostResources = mAppContext.getResources();
            mResources = new Resources(mAssetManager, hostResources.getDisplayMetrics(), hostResources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString(String name) {
        try {
            return mResources.getString(mResources.getIdentifier(name, STRING, pluginApkPackageName));
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "not found string res name:" + name);
        }
        return "";
    }

    @Override
    public ColorStateList getColor(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, COLOR, pluginApkPackageName));
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "not found color res name:" + name);
        }
        return null;
    }

    @Override
    public Drawable getDrawable(String name) {//没有后缀 .jpg
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, DRAWABLE, pluginApkPackageName));
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "not found drawable res name:" + name);
        }
        return null;
    }

    @Override
    public float getDimension(String name) {//
        try {
            return mResources.getDimension(mResources.getIdentifier(name, DIMEN, pluginApkPackageName));
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "not found dimension res name:" + name);
        }
        return -1;
    }

    public Typeface getTypeface(String typefacePath) {
        Typeface typeface = Typeface.DEFAULT;
        try {
            if (!TextUtils.isEmpty(typefacePath)) {
                typeface = Typeface.createFromAsset(mResources.getAssets(), typefacePath);
            }
        } catch (RuntimeException e) {
            Log.i(TAG, "create type face failed :"+typefacePath);
        }
        return typeface;
    }

    /**
     * 更改TextView的TypeFace
     * 在LayoutInflater中调用
     * @param host
     * @param view
     */
    void changeTypeFace(String host, View view) {
        if (!(view instanceof TextView)) {
            return;
        }
        Typeface typeface = getTypeface(ResUtil.getTypeFacePath(view.getContext()));
        ((TextView) view).setTypeface(typeface);
        recorderTypeFace(host, view);
    }

    void changeSkin(String host, View view, AttributeSet attrs) {
        for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {
            String type = attrs.getAttributeName(i);
            String resNameId = attrs.getAttributeValue(i);
            ChangedView changedView = changeSkin(view, type, resNameId);
            recorder(host, changedView);
        }
    }

    private ChangedView changeSkin(View view, String type, String resNameId) {
        SupportAttrType supportAttrType = ResUtil.getSupportAttrType(type);
        String numStr;
        //text="直接文字" @style
        if (supportAttrType != null && resNameId.startsWith("@") && ResUtil.isAllNo(numStr = (resNameId.substring(1)))) {
            int id = Integer.valueOf(numStr);
            String resNameStr = view.getContext().getResources().getResourceEntryName(id);
            String newResNameStr = ResUtil.getNewResName(view.getContext(), resNameStr);
            supportAttrType.apply(!resNameStr.equals(newResNameStr), view, newResNameStr, id);
            return new ChangedView(view, type, resNameId);
        }
        return null;
    }

    private void recorder(String host, ChangedView changedView) {
        if (changedView == null) {
            return;
        }
        ResChangeView views = activityLifecycleCallBack.cacheMap.get(host);
        if (views == null) {
            ResChangeView vs = new ResChangeView(host, new HashSet<ChangedView>());
            vs.views.add(changedView);
            activityLifecycleCallBack.cacheMap.put(host, vs);
        } else {
            views.views.add(changedView);
        }
    }

    private void recorderTypeFace(String host, View view) {
        TypeFaceChangeView views = activityLifecycleCallBack.cacheMapTypeFace.get(host);
        if (views == null) {
            TypeFaceChangeView vs = new TypeFaceChangeView(host, new HashSet<View>());
            vs.views.add(view);
            activityLifecycleCallBack.cacheMapTypeFace.put(host, vs);
        } else {
            views.views.add(view);
        }
    }

}
