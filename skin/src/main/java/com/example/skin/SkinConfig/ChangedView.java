package com.example.skin.SkinConfig;

import android.view.View;

/**
 *
 * @author tys
 * @date 2018/3/15
 */

public class ChangedView {

    public ChangedView(View changedView, String changedAttr, String changedVal) {
        this.changedView = changedView;
        this.changedAttr = changedAttr;
        this.changedVal = changedVal;
    }

    /**
     * 那个view的属性改变了
     */
    public View changedView;

    /**
     * 是哪个属性改变了
     */
    public String changedAttr;

    /**
     * 改变的名称是
     */
    public String changedVal;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChangedView that = (ChangedView) o;

        if (!changedView.equals(that.changedView)) {
            return false;
        }
        return changedAttr.equals(that.changedAttr) && changedVal.equals(that.changedVal);
    }

    @Override
    public int hashCode() {
        int result = changedView.hashCode();
        result = 31 * result + changedAttr.hashCode();
        result = 31 * result + changedVal.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChangedView{" +
                "changedView=" + changedView +
                ", changedAttr='" + changedAttr + '\'' +
                ", changedVal='" + changedVal + '\'' +
                '}';
    }
}
