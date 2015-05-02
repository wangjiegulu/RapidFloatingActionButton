package com.wangjie.rapidfloatingactionbutton;


import com.nineoldandroids.animation.AnimatorSet;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public interface OnRapidFloatingActionListener {
    void onFABClick();

    void toggleContent();

    void expandContent();

    void collapseContent();

    RapidFloatingActionLayout obtainRFALayout();

    RapidFloatingActionButton obtainRFAButton();

    RapidFloatingActionContent obtainRFAContent();

    void onExpandAnimator(AnimatorSet animatorSet);

    void onCollapseAnimator(AnimatorSet animatorSet);

}
