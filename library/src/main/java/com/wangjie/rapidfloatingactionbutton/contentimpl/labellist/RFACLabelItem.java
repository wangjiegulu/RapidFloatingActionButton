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
    private Integer iconNormalColor;
    private Integer iconPressedColor;
    private String label;
    private T wrapper;
    /**
     * 是否加粗字体
     */
    private boolean labelTextBold = true;

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

    public Integer getIconNormalColor() {
        return iconNormalColor;
    }

    public RFACLabelItem<T> setIconNormalColor(Integer iconNormalColor) {
        this.iconNormalColor = iconNormalColor;
        return this;
    }

    public Integer getIconPressedColor() {
        return iconPressedColor;
    }

    public RFACLabelItem<T> setIconPressedColor(Integer iconPressedColor) {
        this.iconPressedColor = iconPressedColor;
        return this;
    }

    public boolean isLabelTextBold() {
        return labelTextBold;
    }

    public RFACLabelItem<T> setLabelTextBold(boolean labelTextBold) {
        this.labelTextBold = labelTextBold;
        return this;
    }
}
