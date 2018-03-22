package com.example.skin.SkinConfig;

import java.util.HashSet;

/**
 * @author tys
 * @date 2018/3/15
 */

public class ResChangeView {

    public ResChangeView(String host, HashSet<ChangedView> views) {
        this.host = host;
        this.views = views;
    }

    public String host;
    /**
     * 那些view的的属性有改变
     */
    public HashSet<ChangedView> views;


}
