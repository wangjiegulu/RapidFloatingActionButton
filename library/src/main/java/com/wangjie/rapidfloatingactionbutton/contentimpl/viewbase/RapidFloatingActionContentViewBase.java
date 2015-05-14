package com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nineoldandroids.animation.AnimatorSet;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.util.ViewUtil;
import com.wangjie.rapidfloatingactionbutton.widget.ViewAnimationDrawable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public abstract class RapidFloatingActionContentViewBase extends RapidFloatingActionContent implements ViewAnimationDrawable.OnViewAnimationDrawableListener {

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
    private ImageView animationIv;
    private ViewAnimationDrawable mViewAnimationDrawable;

    @Override
    protected void initInConstructor() {
        realContentView = getContentView();
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        realContentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(realContentView);

        animationIv = new ImageView(getContext());
        ViewUtil.typeSoftWare(animationIv);
        animationIv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(animationIv, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setRootView(view);

        this.setMeasureAllChildren(true);
        measureAll();

        mViewAnimationDrawable = new ViewAnimationDrawable(realContentView);
        mViewAnimationDrawable.setOnViewAnimationDrawableListener(this);
        animationIv.setImageDrawable(mViewAnimationDrawable);
        mViewAnimationDrawable.setBounds(0, 0, realContentView.getMeasuredWidth(), realContentView.getMeasuredHeight());
//        animationIv.setBackground(mViewAnimationDrawable);

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
        animationIv.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationDrawableOpenEnd() {
        realContentView.setVisibility(VISIBLE);
        animationIv.setVisibility(GONE);
    }

    @Override
    public void onAnimationDrawableCloseStart() {
        realContentView.setVisibility(GONE);
        animationIv.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationDrawableCloseEnd() {
        realContentView.setVisibility(VISIBLE);
        animationIv.setVisibility(GONE);
    }


    /**
     * ********************** 每个item动画 ************************
     */
    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        if (null != mViewAnimationDrawable) {
            animatorSet.playTogether(mViewAnimationDrawable.getOpenAnimator());
        }
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        if (null != mViewAnimationDrawable) {
            animatorSet.playTogether(mViewAnimationDrawable.getCloseAnimator());
        }
    }

}
