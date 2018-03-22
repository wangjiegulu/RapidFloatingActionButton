package com.wangjie.rapidfloatingactionbutton.listener;


import android.animation.AnimatorSet;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

/**
 * 该接口用于把RFAB、RFAC、RFAL组合在一起，RFAH实现该接口
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public interface OnRapidFloatingActionListener {
    /**
     * RFAB被点击
     */
    void onRFABClick();

    /**
     * RFAC内容被展开或者收缩
     */
    void toggleContent();

    /**
     * RFAC内容被展开
     */
    void expandContent();

    /**
     * RFAC内容被收缩
     */
    void collapseContent();

    /**
     * 获取当前的RFAL对象
     *
     * @return
     */
    RapidFloatingActionLayout obtainRFALayout();

    /**
     * 获取当前的RFAB对象
     *
     * @return
     */
    RapidFloatingActionButton obtainRFAButton();

    /**
     * 获取当前的RFAC对象
     *
     * @return
     */
    RapidFloatingActionContent obtainRFAContent();

    /**
     * RFAC内容被展开时需要自定义的额外动画
     *
     * @param animatorSet
     */
    void onExpandAnimator(AnimatorSet animatorSet);

    /**
     * RFAC内容被收缩时需要自定义的额外动画
     *
     * @param animatorSet
     */
    void onCollapseAnimator(AnimatorSet animatorSet);

}
