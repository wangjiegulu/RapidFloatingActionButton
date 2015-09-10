package com.wangjie.rapidfloatingactionbutton.example.cardtest.support.cardview;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/15/14.
 */

import android.graphics.drawable.Drawable;

/**
 * Interface provided by CardView to implementations.
 * <p>
 * Necessary to resolve circular dependency between base CardView and platform implementations.
 */
interface CardViewDelegate {

    void setBackgroundDrawable(Drawable paramDrawable);

    Drawable getBackground();
}
