package com.example.skin.SkinConfig;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author tys
 * @date 2017/9/25
 */

public class ResSpUtil {
    private static final String SHARED_PREFERENCES_NAME = "Setting";

    static String getString(Context context, String name) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(name, "");
    }

    static void putString(Context context, String name, String val) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(name, val).apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                ResSpUtil.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(key, val).apply();
    }

    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, false);
    }
}
