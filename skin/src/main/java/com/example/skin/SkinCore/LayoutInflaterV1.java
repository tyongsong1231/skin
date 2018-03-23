package com.example.skin.SkinCore;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.skin.SkinConfig.CustomAttrApply;
import com.example.skin.SkinConfig.ResUtil;
import com.example.skin.SkinConfig.SkinConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射 LayoutInflater ->createView
 *
 * @author tys
 * @date 2018/3/20
 */

public class LayoutInflaterV1 implements LayoutInflater.Factory2 {
    private static final String TAG = "LayoutInflaterV1";
    private final AppCompatActivity appCompatActivity;
    private Method onCreateViewMethodFactory;
    private Method onCreateViewMethodLayoutInflater;
    private Method createViewMethodLayoutInflater;

    LayoutInflaterV1(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //AppCompatDelegateImplV9  onCreateView
            AppCompatDelegate appCompatDelegate = appCompatActivity.getDelegate();
            appCompatDelegate.installViewFactory();
            if (onCreateViewMethodFactory == null) {
                onCreateViewMethodFactory = appCompatDelegate.getClass().getMethod("onCreateView",
                        View.class, String.class, Context.class, AttributeSet.class);
            }
            view = (View) onCreateViewMethodFactory.invoke(appCompatDelegate, parent, name, context, attrs);

            //LayoutInflater createViewFromTag
            if (view == null) {
                if (onCreateViewMethodLayoutInflater == null) {
                    onCreateViewMethodLayoutInflater = LayoutInflater.class.getDeclaredMethod("onCreateView",
                            View.class, String.class, AttributeSet.class);
                    onCreateViewMethodLayoutInflater.setAccessible(true);
                }
                Field field = LayoutInflater.class.getDeclaredField("mConstructorArgs");
                field.setAccessible(true);
                Object f = field.get(layoutInflater);
                Object[] m = (Object[]) f;
                if (createViewMethodLayoutInflater == null) {
                    createViewMethodLayoutInflater = LayoutInflater.class.getDeclaredMethod("createView",
                            String.class, String.class, AttributeSet.class);
                    createViewMethodLayoutInflater.setAccessible(true);
                }

                final Object lastContext = m[0];
                m[0] = context;
                try {
                    if (-1 == name.indexOf('.')) {
                        view = (View) onCreateViewMethodLayoutInflater.invoke(layoutInflater, parent, name, attrs);
                    } else {
                        //com.example.MyView, android.widget.LinearLayout, android.support.v7.widget
                        view = (View) createViewMethodLayoutInflater.invoke(layoutInflater, name, null, attrs);
                    }
                } finally {
                    m[0] = lastContext;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (view != null) {
            boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.SKIN_NAMESPACE,
                    SkinConfig.SKIN_ATTR_ENABLE, false);
            if (isSkinEnable) {
                ResPluginImpl.getsInstance().changeSkin(appCompatActivity.getClass().getName(), view, attrs);
                //修改自定义view的属性
                if (view instanceof CustomAttrApply) {
                    ((CustomAttrApply) view).apply(view, attrs);
                }
            } else {
                Log.i(TAG, "activity{" + appCompatActivity.getClass().getName() + "}"
                        + " view{" + view.getClass().getName() + "}"
                        + "sure enable " + SkinConfig.SKIN_NAMESPACE);
            }
            if (view instanceof TextView) {
                ResPluginImpl.getsInstance().changeTypeFace(appCompatActivity.getClass().getName(), view);
            }
        }
        return view;
    }

}
