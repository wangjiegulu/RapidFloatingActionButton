package com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.nineoldandroids.animation.AnimatorSet;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.util.ViewUtil;
import com.wangjie.rapidfloatingactionbutton.widget.AnimationView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public abstract class RapidFloatingActionContentViewBase extends RapidFloatingActionContent implements AnimationView.OnViewAnimationDrawableListener {

    private static final String TAG = RapidFloatingActionContentViewBase.class.getSimpleName();

    public RapidFloatingActionContentViewBase(Context context) {
        super(context);
    }

    public RapidFloatingActionContentViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentViewBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private View realContentView;
//    private ImageView animationIv;
    private AnimationView mAnimationView;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.d(TAG, "onAttachedToWindow");
        realContentView = getContentView();
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        realContentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(realContentView);

//        animationIv = new ImageView(getContext());
        mAnimationView = new AnimationView(getContext());
        ViewUtil.typeSoftWare(mAnimationView);
//        mAnimationView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mAnimationView.setLayoutParams(new FrameLayout.LayoutParams(ABTextUtil.dip2px(getContext(), 150), ABTextUtil.dip2px(getContext(), 200)));
        mAnimationView.setDrawView(realContentView);
        mAnimationView.setOnViewAnimationDrawableListener(this);
        view.addView(mAnimationView);

        setRootView(view);

//        this.setMeasureAllChildren(true);
//        measureAll();

//        mAnimationView.initialDraw();

//        animationIv.setImageDrawable(mAnimationView);
//        animationIv.setBackground(mAnimationView);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        Logger.d(TAG, "onScreenStateChanged");
    }

    @Override
    protected void initInConstructor() {


    }

    @NonNull
    protected abstract View getContentView();

    protected abstract void measureAll();

    @Override
    protected void initAfterRFABHelperBuild() {

    }

    @Override
    protected void initialContentViews(View rootView) {
    }

    @Override
    public void onAnimationDrawableOpenStart() {
        realContentView.setVisibility(GONE);
        mAnimationView.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationDrawableOpenEnd() {
        realContentView.setVisibility(VISIBLE);
        mAnimationView.setVisibility(GONE);
    }

    @Override
    public void onAnimationDrawableCloseStart() {
        realContentView.setVisibility(GONE);
        mAnimationView.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationDrawableCloseEnd() {
        realContentView.setVisibility(VISIBLE);
        mAnimationView.setVisibility(GONE);
    }


    /**
     * ********************** 每个item动画 ************************
     */
    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        if (null != mAnimationView) {
            animatorSet.playTogether(mAnimationView.getOpenAnimator());
        }
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        if (null != mAnimationView) {
            animatorSet.playTogether(mAnimationView.getCloseAnimator());
        }
    }

}
