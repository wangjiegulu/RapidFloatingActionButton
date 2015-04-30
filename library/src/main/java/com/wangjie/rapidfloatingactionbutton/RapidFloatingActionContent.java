package com.wangjie.rapidfloatingactionbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.nineoldandroids.animation.AnimatorSet;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public abstract class RapidFloatingActionContent extends FrameLayout {
    public RapidFloatingActionContent(Context context) {
        super(context);
        init();
    }

    public RapidFloatingActionContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RapidFloatingActionContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionContent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {

    }

    protected OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    private View rootView;

    public RapidFloatingActionContent setRootView(View rootView) {
        if (null == rootView) {
            return this;
        }
        this.rootView = rootView;
        this.removeAllViews();
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(this.rootView);
        initialContentViews(this.rootView);
        return this;
    }

    public RapidFloatingActionContent setRootView(int rootViewResId) {
        return setRootView(LayoutInflater.from(getContext()).inflate(rootViewResId, null));
    }

    protected abstract void initialContentViews(View rootView);

    public void onExpandAnimator(AnimatorSet animatorSet) {
    }

    public void onCollapseAnimator(AnimatorSet animatorSet) {
    }


}
