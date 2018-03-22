package com.wangjie.rapidfloatingactionbutton;

import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public abstract class RapidFloatingActionContent extends FrameLayout {
    public RapidFloatingActionContent(Context context) {
        super(context);
        initInConstructor();
    }

    public RapidFloatingActionContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInConstructor();
    }

    public RapidFloatingActionContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionContent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInConstructor();
    }

    /**
     * 构造方法结束时回调
     */
    protected void initInConstructor() {

    }

    /**
     * 设置完毕setContentView后回调
     *
     * @param rootView
     */
    protected abstract void initialContentViews(View rootView);

    /**
     * 当构建了RFAH后回调
     */
    protected void initAfterRFABHelperBuild() {

    }

    protected OnRapidFloatingActionListener onRapidFloatingActionListener;

    protected void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    private View rootView;

    public RapidFloatingActionContent setRootView(View rootView) {
        if (null == rootView) {
            return this;
        }
        this.rootView = rootView;
        this.removeAllViews();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(null == lp){
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        this.setLayoutParams(lp);
        this.addView(this.rootView);
        initialContentViews(this.rootView);
        return this;
    }

    public RapidFloatingActionContent setRootView(int rootViewResId) {
        return setRootView(LayoutInflater.from(getContext()).inflate(rootViewResId, null));
    }


    public void onExpandAnimator(AnimatorSet animatorSet) {
    }

    public void onCollapseAnimator(AnimatorSet animatorSet) {
    }


}
