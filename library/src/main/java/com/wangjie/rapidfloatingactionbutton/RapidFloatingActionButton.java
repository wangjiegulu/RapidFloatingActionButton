package com.wangjie.rapidfloatingactionbutton;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.constants.RFABSize;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingButtonSeparateListener;
import com.wangjie.rapidfloatingactionbutton.util.RFABImageUtil;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;
import com.wangjie.rapidfloatingactionbutton.util.RFABViewUtil;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonDrawable;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonProperties;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionButton extends FrameLayout implements View.OnClickListener {
    /**
     * 默认的identificationCode
     */
    public static final String IDENTIFICATION_CODE_NONE = "";
    /**
     * identificationCode用于确定唯一个RFAB，目前用在通过RapidFloatingActionButtonGroup中获取某一个RFAB
     */
    private String identificationCode = IDENTIFICATION_CODE_NONE;

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(@NonNull String identificationCode) {
        this.identificationCode = identificationCode;
    }

    /**
     * 中间图标
     */
    private Drawable buttonDrawable;
    /**
     * 默认的drawable
     */
    private static final int DEFAULT_BUTTON_DRAWABLE_RES_ID = R.drawable.rfab__drawable_rfab_default;
    /**
     * 中间图片大小24dp
     */
    private int buttonDrawableSize;
    /**
     * 中间图片ImageView
     */
    private ImageView centerDrawableIv;

    public ImageView getCenterDrawableIv() {
        return centerDrawableIv;
    }

    /**
     * 图片的所有属性
     */
    private CircleButtonProperties rfabProperties = new CircleButtonProperties();

    /**
     * 正常状态颜色
     */
    private int normalColor;
    /**
     * 触摸状态颜色
     */
    private int pressedColor;
    /**
     * 按钮图标动画（只有在设置了RFAL和RFAH之后才会有效）
     */
    private ObjectAnimator mDrawableAnimator;
    /**
     * 按钮动画Interpolator（只有在设置了RFAL和RFAH之后才会有效）
     */
    private OvershootInterpolator mOvershootInterpolator;
    /**
     * 使用了RFAH时会自动设置该listener
     */
    private OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    /**
     * 只使用RFAB，不使用RFAC、RFAL、RFAH时可使用这个listener来监听点击事件
     */
    private OnRapidFloatingButtonSeparateListener onRapidFloatingButtonSeparateListener;

    public void setOnRapidFloatingButtonSeparateListener(OnRapidFloatingButtonSeparateListener onRapidFloatingButtonSeparateListener) {
        this.onRapidFloatingButtonSeparateListener = onRapidFloatingButtonSeparateListener;
    }

    public RapidFloatingActionButton(Context context) {
        super(context);
        initAfterConstructor();
    }

    public RapidFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parserAttrs(context, attrs, 0, 0);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parserAttrs(context, attrs, defStyleAttr, 0);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parserAttrs(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
    }

    private void parserAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RapidFloatingActionButton, defStyleAttr, defStyleRes);
        try {
            identificationCode = a.getString(R.styleable.RapidFloatingActionButton_rfab_identification_code);
            if (null == identificationCode) {
                identificationCode = IDENTIFICATION_CODE_NONE;
            }
            buttonDrawable = a.getDrawable(R.styleable.RapidFloatingActionButton_rfab_drawable);
            normalColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_normal, getContext().getResources().getColor(R.color.rfab__color_background_normal));
            pressedColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_pressed, getContext().getResources().getColor(R.color.rfab__color_background_pressed));
            int sizeCode = a.getInt(R.styleable.RapidFloatingActionButton_rfab_size, RFABSize.NORMAL.getCode());
            rfabProperties.setStandardSize(RFABSize.getRFABSizeByCode(sizeCode));
            rfabProperties.setShadowColor(a.getInt(R.styleable.RapidFloatingActionButton_rfab_shadow_color, Color.TRANSPARENT));
            rfabProperties.setShadowDx(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dx, 0));
            rfabProperties.setShadowDy(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dy, 0));
            rfabProperties.setShadowRadius(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_radius, 0));
        } finally {
            a.recycle();
        }

    }

    private void initAfterConstructor() {
        this.setOnClickListener(this);
        // 中间图片大小24dp
        buttonDrawableSize = RFABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAB_DRAWABLE_SIZE_DP);

        refreshRFABDisplay();
    }

    /**
     * 刷新显示
     */
    private void refreshRFABDisplay() {
        if (null == buttonDrawable) {
            buttonDrawable = RFABImageUtil.getResourceDrawableBounded(getContext(), DEFAULT_BUTTON_DRAWABLE_RES_ID, buttonDrawableSize);
        }

        // 设置rfab的背景图片
        CircleButtonDrawable normalDrawable = new CircleButtonDrawable(getContext(), rfabProperties, normalColor);
        RFABViewUtil.setBackgroundDrawable(
                this,
                RFABShape.selectorClickSimple(
                        normalDrawable,
                        new CircleButtonDrawable(getContext(), rfabProperties, pressedColor)
                )
        );

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(LAYER_TYPE_SOFTWARE, normalDrawable.getPaint());
        }

        if (null == centerDrawableIv) {
            this.removeAllViews();
            centerDrawableIv = new ImageView(getContext());
            this.addView(centerDrawableIv);
            LayoutParams lp = new LayoutParams(buttonDrawableSize, buttonDrawableSize);
            lp.gravity = Gravity.CENTER;
            centerDrawableIv.setLayoutParams(lp);
        }
        resetCenterImageView();
    }

    /**
     * 重置图片
     */
    private void resetCenterImageView() {
        buttonDrawable.setBounds(0, 0, buttonDrawableSize, buttonDrawableSize);
        centerDrawableIv.setImageDrawable(buttonDrawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int realSize = rfabProperties.getRealSizePx(getContext());
        setMeasuredDimension(realSize, realSize);
    }

    @Override
    public void onClick(View v) {
        if (null != onRapidFloatingActionListener) {
            onRapidFloatingActionListener.onRFABClick();
        }
        if (null != onRapidFloatingButtonSeparateListener) {
            onRapidFloatingButtonSeparateListener.onRFABClick();
        }
    }

    /**
     * 返回图片属性对象，可以通过调用这个
     *
     * @return
     */
    public CircleButtonProperties getRfabProperties() {
        return rfabProperties;
    }

    /**
     * 设置按钮drawable，注意：需要再调用build()方法才能生效
     *
     * @param buttonDrawable
     */
    public void setButtonDrawable(Drawable buttonDrawable) {
        this.buttonDrawable = buttonDrawable;
    }

    /**
     * 设置普通状态的颜色，注意：需要再调用build()方法才能生效
     *
     * @param normalColor
     */
    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    /**
     * 设置触摸按压状态的颜色，注意：需要再调用build()方法才能生效
     *
     * @param pressedColor
     */
    public void setPressedColor(int pressedColor) {
        this.pressedColor = pressedColor;
    }

    /**
     * 代码中设置完参数后，需要调用build才能生效
     */
    public void build() {
        refreshRFABDisplay();
    }

    /**
     * 默认的button旋转动画（只有在设置了RFAL和RFAH之后才会有效）
     *
     * @param animatorSet
     */
    public void onExpandAnimator(AnimatorSet animatorSet) {
        ensureDrawableAnimator();
        ensureDrawableInterpolator();
        mDrawableAnimator.cancel();
        mDrawableAnimator.setTarget(centerDrawableIv);
        mDrawableAnimator.setFloatValues(0, -45f);
        mDrawableAnimator.setPropertyName("rotation");
        mDrawableAnimator.setInterpolator(mOvershootInterpolator);
        animatorSet.playTogether(mDrawableAnimator);
    }

    public void onCollapseAnimator(AnimatorSet animatorSet) {
        ensureDrawableAnimator();
        ensureDrawableInterpolator();
        mDrawableAnimator.cancel();
        mDrawableAnimator.setTarget(centerDrawableIv);
        mDrawableAnimator.setFloatValues(-45f, 0);
        mDrawableAnimator.setPropertyName("rotation");
        mDrawableAnimator.setInterpolator(mOvershootInterpolator);
        animatorSet.playTogether(mDrawableAnimator);
    }

    /**
     * 只有在设置了RFAL和RFAH之后才去生成ObjectAnimator实例
     */
    private void ensureDrawableAnimator() {
        if (null == mDrawableAnimator) {
            mDrawableAnimator = new ObjectAnimator();
        }
    }

    /**
     * 只有在设置了RFAL和RFAH之后才去生成Interpolator实例
     */
    private void ensureDrawableInterpolator() {
        if (null == mOvershootInterpolator) {
            mOvershootInterpolator = new OvershootInterpolator();
        }
    }

}
