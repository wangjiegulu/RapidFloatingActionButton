package com.wangjie.rapidfloatingactionbutton.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-28
 * Time: 上午10:12
 */
public class RFABImageUtil {
    public static final String TAG = RFABImageUtil.class.getSimpleName();

    /***********************************图片初始化 BEGIN*************************************/
    /**
     * 从Resource中获取Drawable，并初始化bound
     *
     * @param context
     * @param drawableResId
     * @param bound
     * @return
     */
    public static Drawable getResourceDrawableBounded(Context context, int drawableResId, int bound) {
        Drawable drawable = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = context.getResources().getDrawable(drawableResId, null);
            } else {
                drawable = context.getResources().getDrawable(drawableResId);
            }
            drawable.setBounds(0, 0, bound, bound);
        } catch (Exception ex) {
            Log.e(TAG, "", ex);
        }
        return drawable;
    }

}
