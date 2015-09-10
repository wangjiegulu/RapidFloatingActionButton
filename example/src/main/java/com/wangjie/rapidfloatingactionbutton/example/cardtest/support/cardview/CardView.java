package com.wangjie.rapidfloatingactionbutton.example.cardtest.support.cardview;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.wangjie.rapidfloatingactionbutton.example.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/15/14.
 */

/**
 * A ViewGroup with a rounded corner background and shadow behind.
 * <p/>
 * CardView uses <code>elevation</code> property on L for shadows and falls back to a custom shadow implementation on
 * older platforms.
 * <p/>
 * Due to expensive nature of rounded corner clipping, on platforms before L, CardView does not clip its children that
 * intersect with rounded corners. Instead, it adds padding to avoid such intersection.
 *
 * @attr ref android.support.v7.cardview.R.styleable#CardView_cardBackgroundColor
 * @attr ref android.support.v7.cardview.R.styleable#CardView_cardCornerRadius
 */
public class CardView extends FrameLayout implements CardViewDelegate {

    private final static CardViewImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new CardViewJellybeanMr1();
        } else {
            IMPL = new CardViewEclairMr1();
        }
        IMPL.initStatic();
    }

    public CardView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardView, defStyleAttr, R.style.CardView_Light);
        int backgroundColor = a.getColor(R.styleable.CardView_cardBackgroundColor, 0);
        float radius = a.getDimension(R.styleable.CardView_cardCornerRadius, 0);

        CardShadow cardShadow = new CardShadow();
        cardShadow.setShadowStartColor(a.getColor(R.styleable.CardView_shadowStartColor, context.getResources().getColor(R.color.cardview_shadow_start_color)));
        cardShadow.setShadowEndColor(a.getColor(R.styleable.CardView_shadowEndColor, context.getResources().getColor(R.color.cardview_shadow_end_color)));
        cardShadow.setShadowSize(a.getDimension(R.styleable.CardView_shadowSize, context.getResources().getDimension(R.dimen.cardview_shadow_size)));

        a.recycle();
        IMPL.initialize(this, context, backgroundColor, radius, cardShadow);
    }

    /**
     * Updates the corner radius of the CardView.
     *
     * @param radius The radius in pixels of the corners of the rectangle shape
     * @attr ref android.support.v7.cardview.R.styleable#CardView_cardCornerRadius
     * @see #setRadius(float)
     */
    public void setRadius(float radius) {
        IMPL.setRadius(this, radius);
    }

    /**
     * Returns the corner radius of the CardView.
     *
     * @return Corner radius of the CardView
     * @see #getRadius()
     */
    public float getRadius() {
        return IMPL.getRadius(this);
    }
}
