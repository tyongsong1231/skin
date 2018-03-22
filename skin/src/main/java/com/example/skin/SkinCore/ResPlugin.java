package com.example.skin.SkinCore;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

/**
 * @author tys
 * @date 2017/9/23
 */

public interface ResPlugin {

    /**
     * R文件中的类名，一般都不会更改
     */
    String DRAWABLE = "drawable";
    String DIMEN = "dimen";
    String COLOR = "color";
    String STRING = "string";

    /**
     * 设置资源
     * @param activity
     * @param suffixName
     */
    void setRes(Activity activity, String suffixName);

    /**
     * 全局替换字体
     * @param context
     * @param typeFaceName
     */
    void setTypeFace(Context context, String typeFaceName);

    /**
     * 设置StatusBar颜色
     * @param activity
     * @param nameInPlugin 在插件中该颜色的名称
     */
    void setStatusBarColor(Activity activity, String nameInPlugin);

    /**
     * 设置NavigateBar颜色
     * @param activity
     * @param nameInPlugin 在插件中该颜色的名称
     */
    void setNavigateBarColor(Activity activity, String nameInPlugin);

    /**
     * 加载插件文件
     * @param pluginApkPath
     */
    void loadPluginFile(String pluginApkPath);


    ColorStateList getColor(String pluginResName);

    Drawable getDrawable(String pluginResName);

    String getString(String pluginResName);

    float getDimension(String pluginResName);
}
