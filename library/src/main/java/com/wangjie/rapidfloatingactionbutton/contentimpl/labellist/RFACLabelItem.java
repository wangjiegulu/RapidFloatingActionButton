package com.wangjie.rapidfloatingactionbutton.contentimpl.labellist;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RFACLabelItem<T> implements Serializable {
    private int resId = -1;
    private Drawable drawable;
    private String label;
    private T wrapper;

    public RFACLabelItem() {
    }

    public RFACLabelItem(int resId, String label) {
        this.resId = resId;
        this.label = label;
    }

    public int getResId() {
        return resId;
    }

    public RFACLabelItem<T> setResId(int resId) {
        this.resId = resId;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public RFACLabelItem<T> setLabel(String label) {
        this.label = label;
        return this;
    }

    public T getWrapper() {
        return wrapper;
    }

    public RFACLabelItem<T> setWrapper(T wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public RFACLabelItem<T> setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    @Override
    public String toString() {
        return "RFACLabelItem{" +
                "resId=" + resId +
                ", drawable=" + drawable +
                ", label='" + label + '\'' +
                ", wrapper=" + wrapper +
                '}';
    }
}
