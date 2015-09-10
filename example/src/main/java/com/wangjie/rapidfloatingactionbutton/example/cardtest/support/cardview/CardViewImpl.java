package com.wangjie.rapidfloatingactionbutton.example.cardtest.support.cardview;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/15/14.
 */

import android.content.Context;

/**
 * Interface for platform specific CardView implementations.
 */
interface CardViewImpl {

    void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius, CardShadow cardShadow);

    void setRadius(CardViewDelegate cardView, float radius);

    float getRadius(CardViewDelegate cardView);

    void initStatic();
}
