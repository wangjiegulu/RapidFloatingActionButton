package com.wangjie.rapidfloatingactionbutton.example.cardtest.support.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/15/14.
 */
class CardViewEclairMr1 implements CardViewImpl {

    final RectF sCornerRect = new RectF();

    @Override
    public void initStatic() {
        // Draws a round rect using 7 draw operations. This is faster than using
        // canvas.drawRoundRect before JBMR1 because API 11-16 used alpha mask textures to draw
        // shapes.
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() {

            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
                final float twoRadius = cornerRadius * 2;
                final float innerWidth = bounds.width() - twoRadius;
                final float innerHeight = bounds.height() - twoRadius;
                sCornerRect.set(bounds.left, bounds.top, bounds.left + (cornerRadius * 2), bounds.top
                        + (cornerRadius * 2));

                canvas.drawArc(sCornerRect, 180, 90, true, paint);
                sCornerRect.offset(innerWidth, 0);
                canvas.drawArc(sCornerRect, 270, 90, true, paint);
                sCornerRect.offset(0, innerHeight);
                canvas.drawArc(sCornerRect, 0, 90, true, paint);
                sCornerRect.offset(-innerWidth, 0);
                canvas.drawArc(sCornerRect, 90, 90, true, paint);

                // draw top and bottom pieces
                canvas.drawRect(bounds.left + cornerRadius, bounds.top, bounds.right - cornerRadius, bounds.top
                        + cornerRadius, paint);
                canvas.drawRect(bounds.left + cornerRadius, bounds.bottom - cornerRadius, bounds.right - cornerRadius,
                        bounds.bottom, paint);

                // center
                canvas.drawRect(bounds.left, bounds.top + cornerRadius, bounds.right, bounds.bottom - cornerRadius,
                        paint);
            }
        };
    }

    @Override
    public void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius, CardShadow cardShadow) {
        RoundRectDrawableWithShadow background = new RoundRectDrawableWithShadow(context.getResources(),
                backgroundColor, radius, cardShadow);
        cardView.setBackgroundDrawable(background);
    }

    @Override
    public void setRadius(CardViewDelegate cardView, float radius) {
        ((RoundRectDrawableWithShadow)cardView.getBackground()).setCornerRadius(radius);
    }

    @Override
    public float getRadius(CardViewDelegate cardView) {
        return ((RoundRectDrawableWithShadow)cardView.getBackground()).getCornerRadius();
    }
}
