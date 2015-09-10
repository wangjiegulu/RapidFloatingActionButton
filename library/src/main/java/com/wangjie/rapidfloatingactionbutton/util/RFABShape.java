package com.wangjie.rapidfloatingactionbutton.util;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/12/14.
 */
public class RFABShape {

    /**
     * 生成圆角的背景
     *
     * @param color
     * @return
     */
    public static ShapeDrawable generateCornerShapeDrawable(int color, int corner) {
        return generateCornerShapeDrawable(color, corner, corner, corner, corner);
    }

    public static ShapeDrawable generateCornerShapeDrawable(int color, int topLeftCorner, int topRightCorner, int bottomRightCorner, int bottomLeftCorner) {
        Shape shape = new RoundRectShape(new float[]{topLeftCorner, topLeftCorner, topRightCorner, topRightCorner, bottomRightCorner, bottomRightCorner, bottomLeftCorner, bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.FILL);
        return sd;
    }

    public static ShapeDrawable generateCornerStrokeDrawable(int color, float width, int corner) {
        return generateCornerStrokeDrawable(color, width, corner, corner, corner, corner);
    }

    public static ShapeDrawable generateCornerStrokeDrawable(int color, float width, int topLeftCorner, int topRightCorner, int bottomRightCorner, int bottomLeftCorner) {
        Shape shape = new RoundRectShape(new float[]{topLeftCorner, topLeftCorner, topRightCorner, topRightCorner, bottomRightCorner, bottomRightCorner, bottomLeftCorner, bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        sd.getPaint().setAntiAlias(true);
        sd.getPaint().setStrokeWidth(width);
        return sd;
    }

    public static StateListDrawable selectorClickSimple(Drawable normal, Drawable pressed) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    public static StateListDrawable selectorClickColorCornerSimple(int normalColor, int pressedColor, int corner) {
        return selectorClickSimple(
                generateCornerShapeDrawable(normalColor, corner),
                generateCornerShapeDrawable(pressedColor, corner)
        );
    }

    public static ShapeDrawable generateBackgroundDrawable(int color) {
        Shape shape = new OvalShape();
        ShapeDrawable sd = new ShapeDrawable(shape);
        Paint paint = sd.getPaint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return sd;
    }


    public static Drawable selectorCornerRippleCompat(int normalColor, int pressedColor) {
        return selectorCornerRippleCompat(normalColor, pressedColor, 0);
    }
    public static Drawable selectorCornerRippleCompat(int normalColor, int pressedColor, int corner) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = new RippleDrawable(RFABViewUtil.createColorStateList(normalColor, pressedColor),
                    generateCornerShapeDrawable(normalColor, corner), null);
        }else{
            drawable = selectorClickColorCornerSimple(normalColor, pressedColor, corner);
        }
        return drawable;
    }


}
