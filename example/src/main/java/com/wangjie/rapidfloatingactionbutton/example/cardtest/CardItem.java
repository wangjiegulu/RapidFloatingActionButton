package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/17/15.
 */
public class CardItem implements Serializable{
    private String name;
    private int resId;

    public CardItem() {
    }

    public String getName() {
        return name;
    }

    public CardItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getResId() {
        return resId;
    }

    public CardItem setResId(int resId) {
        this.resId = resId;
        return this;
    }
}
