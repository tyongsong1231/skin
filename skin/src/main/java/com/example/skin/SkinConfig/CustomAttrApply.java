package com.example.skin.SkinConfig;

import android.util.AttributeSet;
import android.view.View;

/**
 * @author tys
 * @date 2018/3/22
 */

public interface CustomAttrApply {


    /**
     * 自定义view实现此接口，在LayoutInflater解析后回调此接口。设置skin:enable
     *
     * @param view  自定义的viw
     * @param attrs 属性
     */
    void apply(View view, AttributeSet attrs);
}
