package com.example.skin.SkinConfig;

import android.view.View;

import java.util.HashSet;

/**
 * 设置TextView的TypeFace
 *
 * @author tys
 * @date 2018/3/21
 */

public class TypeFaceChangeView {
    public TypeFaceChangeView(String host, HashSet<View> views) {
        this.host = host;
        this.views = views;
    }

    public String host;
    /**
     * 那些view的的属性有改变
     */
    public HashSet<View> views;

}
