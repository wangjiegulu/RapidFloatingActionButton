package com.wangjie.rapidfloatingactionbutton.example.cardtest.support.cardview;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/16/14.
 */
public class CardShadow implements Serializable{

    private int shadowStartColor;
    private int shadowEndColor;
    private float shadowSize;

    public int getShadowStartColor() {
        return shadowStartColor;
    }

    public void setShadowStartColor(int shadowStartColor) {
        this.shadowStartColor = shadowStartColor;
    }

    public int getShadowEndColor() {
        return shadowEndColor;
    }

    public void setShadowEndColor(int shadowEndColor) {
        this.shadowEndColor = shadowEndColor;
    }

    public float getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(float shadowSize) {
        this.shadowSize = shadowSize;
    }
}
