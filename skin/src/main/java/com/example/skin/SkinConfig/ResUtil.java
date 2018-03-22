package com.example.skin.SkinConfig;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.Log;

import com.example.skin.SkinCore.SupportAttrType;

import java.util.regex.Pattern;

/**
 * @author tys
 * @date 2017/9/24
 */

public class ResUtil {

    /**
     * 资源更换的匹配规则
     *
     * @param context
     * @param resName 原始资源名称
     * @return 新资源的名称 oldResName_exceptResSuffix
     */
    public static String getNewResName(Context context, String resName) {
        String exceptResSuffix;
        if (!TextUtils.isEmpty(SkinConfig.SKIN_RES_NAME_SUFFIX_VAL)) {
            exceptResSuffix = SkinConfig.SKIN_RES_NAME_SUFFIX_VAL;
        } else {
            exceptResSuffix = ResSpUtil.getString(context, SkinConfig.SKIN_RES_NAME_SUFFIX_KEY);
            SkinConfig.SKIN_RES_NAME_SUFFIX_VAL = exceptResSuffix;
        }
        if (!TextUtils.isEmpty(exceptResSuffix)) {
            int index;
            if ((index = resName.lastIndexOf(SkinConfig.SEPARATOR)) > 0) {
                return resName.substring(0, index) + SkinConfig.SEPARATOR + exceptResSuffix;
            } else {
                Log.i(TAG, "not found res name separator:" + SkinConfig.SEPARATOR);
                return resName;
            }
        } else {
            return resName;
        }
    }

    private static final String TAG = "ResUtil";

    /**
     * @param type
     * @return
     */
    public static SupportAttrType getSupportAttrType(String type) {
        for (SupportAttrType supportType : SupportAttrType.values()) {
            String[] types = supportType.getTypes();
            for (String t : types) {
                if (type.equals(t)) {
                    supportType.setType(type);
                    return supportType;
                }
            }
        }
        return null;
    }

    /**
     * 获取主题的一些属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static int[] getResId(Context context, int... attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return resIds;
    }


    public static boolean isAllNo(String numStr) {
        String str = "[0-9]*";
        Pattern pattern = Pattern.compile(str);
        return pattern.matcher(numStr).matches();
    }


    private static void putStringToSp(Context context, String key, String val) {
        ResSpUtil.putString(context, key, val);
    }

    private static String getStringFromSp(Context context, String key) {
        return ResSpUtil.getString(context, key);
    }


    /**
     * 设置资源文件的后缀名称，根据该名称在插件文件中匹配对应的资源
     *
     * @param context
     * @param suffixName
     */
    public static void setResSuffix(Context context, String suffixName) {
        SkinConfig.SKIN_RES_NAME_SUFFIX_VAL = suffixName;
        ResUtil.putStringToSp(context, SkinConfig.SKIN_RES_NAME_SUFFIX_KEY, suffixName);
    }

    public static String getResSuffix(Context context) {
        if (!TextUtils.isEmpty(SkinConfig.SKIN_RES_NAME_SUFFIX_VAL)) {
            return SkinConfig.SKIN_RES_NAME_SUFFIX_VAL;
        }
        SkinConfig.SKIN_RES_NAME_SUFFIX_VAL = ResUtil.getStringFromSp(context, SkinConfig.SKIN_RES_NAME_SUFFIX_KEY);
        return SkinConfig.SKIN_RES_NAME_SUFFIX_VAL;
    }


    /**
     * 设置字体文件的名称，默认目录assets/font
     *
     * @param context
     * @param typeFaceName
     */
    public static void setTypeFaceName(Context context, String typeFaceName) {
        SkinConfig.SKIN_TYPEFACE_NAME_VAL = typeFaceName;
        ResUtil.putStringToSp(context, SkinConfig.SKIN_TYPEFACE_NAME_KEY, typeFaceName);
    }

    public static String getTypeFacePath(Context context) {
        if (!TextUtils.isEmpty(SkinConfig.SKIN_TYPEFACE_NAME_VAL)) {
            return SkinConfig.SKIN_TYPEFACE_DIR + SkinConfig.SKIN_TYPEFACE_NAME_VAL;
        }
        SkinConfig.SKIN_TYPEFACE_NAME_VAL = ResUtil.getStringFromSp(context, SkinConfig.SKIN_TYPEFACE_NAME_KEY);
        return SkinConfig.SKIN_TYPEFACE_DIR + SkinConfig.SKIN_TYPEFACE_NAME_VAL;
    }


    public static void setStatusBarColorName(Context context, String nameInPlugin) {
        SkinConfig.STATUS_BAR_COLOR_VAL = nameInPlugin;
        ResUtil.putStringToSp(context, SkinConfig.STATUS_BAR_COLOR_KEY, nameInPlugin);
    }

    public static String getStatusBarColorName(Context context) {
        if (!TextUtils.isEmpty(SkinConfig.STATUS_BAR_COLOR_VAL)) {
            return SkinConfig.STATUS_BAR_COLOR_VAL;
        }
        SkinConfig.STATUS_BAR_COLOR_VAL = ResUtil.getStringFromSp(context, SkinConfig.STATUS_BAR_COLOR_KEY);
        return SkinConfig.STATUS_BAR_COLOR_VAL;
    }

    public static void setNavigateBarColorName(Context context, String nameInPlugin) {
        SkinConfig.NAVIGATION_BAR_COLOR_VAL = nameInPlugin;
        ResUtil.putStringToSp(context, SkinConfig.NAVIGATION_BAR_COLOR_KEY, nameInPlugin);
    }

    public static String getNavigateBarColorName(Context context) {
        if (!TextUtils.isEmpty(SkinConfig.NAVIGATION_BAR_COLOR_VAL)) {
            return SkinConfig.NAVIGATION_BAR_COLOR_VAL;
        }
        SkinConfig.NAVIGATION_BAR_COLOR_VAL = ResUtil.getStringFromSp(context, SkinConfig.NAVIGATION_BAR_COLOR_KEY);
        return SkinConfig.NAVIGATION_BAR_COLOR_VAL;
    }

}
