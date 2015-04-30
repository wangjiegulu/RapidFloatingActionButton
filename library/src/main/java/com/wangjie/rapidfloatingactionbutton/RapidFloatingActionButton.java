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

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionButton extends FrameLayout implements View.OnClickListener {

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
        pxSize = ABTextUtil.dip2px(context, RFABSize.getDpSizeByCode(sizeCode));

        a.recycle();

    }

    private void init() {
        // 中间图片大小24dp
        int drawableSize = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAB_DRAWABLE_SIZE_DP);
        ABViewUtil.setBackgroundDrawable(this, ABShape.selectorClickColorCornerSimple(normalColor, pressedColor, pxSize / 2));

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

    private int drawableResId = R.drawable.ico_add;
    private ImageView centerIv;

    private int pxSize;
    private int normalColor;
    private int pressedColor;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(pxSize, pxSize);
    }

    @Override
    public void onClick(View v) {
        if (null != onRapidFloatingActionListener) {
            onRapidFloatingActionListener.onFABClick();
        }
    }

    public int getRFABSize() {
        return pxSize;
    }

}
