package com.example.skin.SkinCore;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.skin.SkinConfig.SkinConfig;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class LayoutInflaterV2 implements LayoutInflater.Factory2 {
    private static final String[] M_CLASS_PREFIX_LIST = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    private static final String TAG = "LayoutInflaterV2";
    private static final Map<String, Constructor<? extends View>> M_CONSTRUCTOR_MAP = new HashMap<>();
    private static final Class<?>[] M_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
    private final Activity activity;

    public LayoutInflaterV2(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createViewFromTag(name, context, attrs);
        if (null == view) {
            view = createView(name, context, attrs);
        }
        if (null != view) {
            boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.SKIN_NAMESPACE,
                    SkinConfig.SKIN_ATTR_ENABLE, false);
            if (isSkinEnable) {
                ResPluginImpl.getsInstance().changeSkin(activity.getClass().getName(), view, attrs);
            } else {
                Log.i(TAG, "activity{" + activity.getClass().getName() + "}"
                        + " view{" + view.getClass().getName() + "}"
                        + "sure enable " + SkinConfig.SKIN_NAMESPACE);
            }

            if (view instanceof TextView) {
                ResPluginImpl.getsInstance().changeTypeFace(activity.getClass().getName(), view);
            }
        }
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        if (-1 != name.indexOf('.')) {
            return null;
        }
        //com.example.MyView, android.widget.LinearLayout, android.support.v7.widget
        for (String aMClassPrefixList : M_CLASS_PREFIX_LIST) {
            return createView(aMClassPrefixList + name, context, attrs);
        }
        return null;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = findConstructor(context, name);
        try {
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = M_CONSTRUCTOR_MAP.get(name);
        if (null == constructor) {
            try {
                Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = clazz.getConstructor(M_CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                M_CONSTRUCTOR_MAP.put(name, constructor);
            } catch (Exception e) {
            }
        }
        return constructor;
    }

}
