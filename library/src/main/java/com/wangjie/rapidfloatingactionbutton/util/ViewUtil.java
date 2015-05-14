package com.wangjie.rapidfloatingactionbutton.util;

import android.os.Build;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/7/15.
 */
public class ViewUtil {
    public static void typeSoftWare(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
