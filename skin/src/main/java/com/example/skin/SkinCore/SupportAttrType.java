package com.example.skin.SkinCore;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skin.SkinConfig.SkinConfig;


/**
 * @author tys
 * @date 2017/9/23
 */

public enum SupportAttrType {

    /**
     * 切换background的操作对象
     */
    BACKGROUND("background") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            String type = ((View) view).getResources().getResourceTypeName(srcResId);
            Object res = null;
            switch (type) {
                case ResPlugin.COLOR:
                    res = isFromPlugin ? getPluginResourcesManger().getColor(pluginResName) :
                            ((View) view).getResources().getColorStateList(srcResId);
                    if (res == null) {
                        res = getPluginResourcesManger().getDrawable(pluginResName);
                    }
                    break;
                case ResPlugin.DRAWABLE:
                    res = isFromPlugin ? getPluginResourcesManger().getDrawable(pluginResName) :
                            ((View) view).getResources().getDrawable(srcResId);
                    if (res == null) {
                        res = getPluginResourcesManger().getColor(pluginResName);
                    }
                    break;
                //xml selector
                default:
                    break;
            }
            if (res != null && res instanceof Drawable) {
                ((View) view).setBackground((Drawable) res);
            }
            if (res != null && res instanceof ColorStateList) {
                ((View) view).setBackgroundColor(((ColorStateList) (res)).getDefaultColor());
            }
        }
    },

    /**
     * 切换text的操作对象
     */
    TEXT("text") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            String str = getString(isFromPlugin, ((View)view).getContext(), pluginResName, srcResId);
            if (view instanceof TextView && !TextUtils.isEmpty(str)) {
                ((TextView) view).setText(str);
            }
        }
    },

    /**
     * 切换src的操作对象
     */
    SRC("src") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            Drawable drawable = getDrawable(isFromPlugin, ((View) view).getContext(), pluginResName, srcResId);
            if (view instanceof ImageView && drawable != null) {
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    },

    /**
     * 切换textColor的操作对象
     */
    TEXT_COLOR("textColor") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            ColorStateList color = getColor(isFromPlugin, ((View) view).getContext(), pluginResName, srcResId);
            if (view instanceof TextView && null != color) {
                ((TextView) view).setTextColor(color);
            }
        }
    },

    /**
     * 切换textSize的操作对象
     */
    TEXT_SIZE("textSize") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            float size = getDimension(isFromPlugin, ((View)view).getContext(), pluginResName, srcResId);
            if (view instanceof TextView && size >= 0) {
                ((TextView) view).setTextSize(size);
            }
        }
    },

    DRAWABLE_PLAN("drawableLeft", "drawableRight", "drawableTop", "drawableBottom") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            Drawable drawable = getDrawable(isFromPlugin, ((View) view).getContext(), pluginResName, srcResId);
            if (view instanceof TextView && drawable != null) {
                switch (this.type[0]) {
                    case "drawableLeft":
                        ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        break;
                    case "drawableTop":
                        ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                        break;
                    case "drawableRight":
                        ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                        break;
                    case "drawableBottom":
                        ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                        break;
                    default:
                        break;
                }
            }
        }
    },

    STATUS_BAR("statusBar") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorStateList color;
                color = getColor(isFromPlugin, ((Activity) view).getBaseContext(),
                        pluginResName, srcResId);
                if (color == null) {
                    //沒有配置status bar颜色或者配置了但是找不到。 使用插件中colorPrimaryDark的颜色
                    color = getColor(true, null, SkinConfig.COLO_PRINARY_DARK, 0);
                }
                if (color != null) {
                    ((Activity) view).getWindow().setStatusBarColor(color.getDefaultColor());
                }
            }
        }
    },

    NAVIGATION_BAR("navigationBar") {
        @Override
        public <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorStateList color = getColor(isFromPlugin, ((Activity) view).getBaseContext(),
                        pluginResName, srcResId);
                if (null != color) {
                    ((Activity) view).getWindow().setNavigationBarColor(color.getDefaultColor());
                }
            }
        }
    };

    Drawable getDrawable(boolean isFromPlugin, Context context, String pluginResName, int srcResId) {
        Drawable drawable;
        if (isFromPlugin) {
            drawable = getPluginResourcesManger().getDrawable(pluginResName);
        } else {
            drawable = context.getResources().getDrawable(srcResId);
        }
        return drawable;
    }

    ColorStateList getColor(boolean isFromPlugin, Context context, String pluginResName, int srcResId) {
        ColorStateList color;
        if (isFromPlugin) {
            color = getPluginResourcesManger().getColor(pluginResName);
        } else {
            color = context.getResources().getColorStateList(srcResId);
        }
        return color;
    }

    String getString(boolean isFromPlugin, Context context, String pluginResName, int srcResId) {
        String str;
        if (isFromPlugin) {
            str = getPluginResourcesManger().getString(pluginResName);
        } else {
            str = context.getString(srcResId);
        }
        return str;
    }

    float getDimension(boolean isFromPlugin, Context context, String pluginResName, int srcResId){
        float size = 0;
        if (isFromPlugin) {
            size = getPluginResourcesManger().getDimension(pluginResName);
        } else {
            size = context.getResources().getDimension(srcResId);
        }
        return size;
    }

    ResPlugin getPluginResourcesManger() {
        return ResPluginImpl.getsInstance();
    }

    String[] type;

    SupportAttrType(String... type) {
        this.type = type;
    }

    public String[] getTypes() {
        return type;
    }

    public void setType(String t) {
        type[0] = t;
    }

    /**
     * 将插件中资源的熟悉应用到view
     *
     * @param isFromPlugin  新的资源是否来自plugin
     * @param view          属性改变的view，或者是activity(设置status bar)
     * @param pluginResName 插件中资源的名称
     * @param srcResId      源资源的id，在isFromPlugin为false时使用
     */
    public abstract <T> void apply(boolean isFromPlugin, T view, String pluginResName, int srcResId);

}
