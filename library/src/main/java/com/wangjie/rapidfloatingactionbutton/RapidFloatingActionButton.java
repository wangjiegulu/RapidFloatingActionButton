package com.wangjie.rapidfloatingactionbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.constants.RFABSize;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonDrawable;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonProperties;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionButton extends FrameLayout implements View.OnClickListener {


    private int drawableResId = R.drawable.ico_add;
    private ImageView centerIv;

    private CircleButtonProperties rfabProperties = new CircleButtonProperties();

    /**
     * 正常状态颜色
     */
    private int normalColor;
    /**
     * 触摸状态颜色
     */
    private int pressedColor;

    public RapidFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public RapidFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parserAttrs(context, attrs, 0, 0);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parserAttrs(context, attrs, defStyleAttr, 0);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parserAttrs(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void parserAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RapidFloatingActionButton, defStyleAttr, defStyleRes);
        normalColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_normal, Color.WHITE);
        pressedColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_pressed, getContext().getResources().getColor(R.color.rfab__color_background_pressed));
        int sizeCode = a.getInt(R.styleable.RapidFloatingActionButton_rfab_size, RFABSize.NORMAL.getCode());
        rfabProperties.setStandardSize(RFABSize.getRFABSizeByCode(sizeCode));
        rfabProperties.setShadowColor(a.getInt(R.styleable.RapidFloatingActionButton_rfab_shadow_color, Color.TRANSPARENT));
        rfabProperties.setShadowDx(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dx, 0));
        rfabProperties.setShadowDy(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dy, 0));
        rfabProperties.setShadowRadius(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_radius, 0));

        a.recycle();

    }

    private void init() {
        // 中间图片大小24dp
        int drawableSize = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAB_DRAWABLE_SIZE_DP);

        CircleButtonDrawable normalDrawable = new CircleButtonDrawable(getContext(), rfabProperties, normalColor);
        ABViewUtil.setBackgroundDrawable(
                this,
                ABShape.selectorClickSimple(
                        normalDrawable,
                        new CircleButtonDrawable(getContext(), rfabProperties, pressedColor)
                )
        );

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(LAYER_TYPE_SOFTWARE, normalDrawable.getPaint());
        }

        this.setOnClickListener(this);
        if (null == centerIv) {
            this.removeAllViews();
            centerIv = new ImageView(getContext());
            this.addView(centerIv);
            LayoutParams lp = new LayoutParams(drawableSize, drawableSize);
            lp.gravity = Gravity.CENTER;
            centerIv.setLayoutParams(lp);
            centerIv.setImageDrawable(ABImageProcess.getResourceDrawableBounded(getContext(), drawableResId, drawableSize));
        }

    }

    private OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
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
            onRapidFloatingActionListener.onFABClick();
        }
    }

    public CircleButtonProperties getRfabProperties() {
        return rfabProperties;
    }
}
