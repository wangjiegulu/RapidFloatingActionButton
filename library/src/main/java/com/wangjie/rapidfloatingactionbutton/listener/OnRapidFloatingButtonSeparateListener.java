package com.wangjie.rapidfloatingactionbutton.listener;

/**
 * 建议该监听器只在使用单个RFAB（而不使用RFAC和RFAL）时使用，只关注RFAB的点击事件而不会展开或者收缩RFAC
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
public interface OnRapidFloatingButtonSeparateListener {
    /**
     * RFAB被点击
     */
    void onRFABClick();
}
