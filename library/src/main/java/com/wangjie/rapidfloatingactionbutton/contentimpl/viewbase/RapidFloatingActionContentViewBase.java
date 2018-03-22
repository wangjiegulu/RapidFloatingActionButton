package com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.animation.AnimatorSet;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
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
    private AnimationView mAnimationView;


    @NonNull
    protected abstract View getContentView();

    @Override
    protected void initAfterRFABHelperBuild() {
        realContentView = getContentView();
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        realContentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(realContentView);

        mAnimationView = new AnimationView(getContext());
        ViewCompat.setLayerType(mAnimationView, ViewCompat.LAYER_TYPE_SOFTWARE, null);
        mAnimationView.setLayoutParams(realContentView.getLayoutParams());
        mAnimationView.setDrawView(realContentView);
        mAnimationView.setOnViewAnimationDrawableListener(this);
        view.addView(mAnimationView);

        setRootView(view);

        this.setMeasureAllChildren(true);
//        this.measure(this.getMeasuredWidth(), this.getMeasuredHeight());
        this.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mAnimationView.setLayoutParams(new FrameLayout.LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight()));
        mAnimationView.setMinRadius(onRapidFloatingActionListener.obtainRFAButton().getRfabProperties().getRealSizePx(getContext()) / 2);
        mAnimationView.initialDraw();

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
