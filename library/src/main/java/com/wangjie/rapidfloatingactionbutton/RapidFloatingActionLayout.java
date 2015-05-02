package com.wangjie.rapidfloatingactionbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionLayout extends RelativeLayout implements OnClickListener {
    public RapidFloatingActionLayout(Context context) {
        super(context);
        initAfterConstructor();
    }

    public RapidFloatingActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAfterConstructor();
    }

    public RapidFloatingActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
    }

    public static final long ANIMATION_DURATION = 150/*ms*/;

    private void initAfterConstructor() {

    }

    private OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    private View fillBackgroundView;
    private RapidFloatingActionContent contentView;

    public RapidFloatingActionLayout setContentView(RapidFloatingActionContent contentView) {
        if (null == contentView) {
            throw new RuntimeException("contentView can not be null");
        }
        if (null != this.contentView) {
            throw new RuntimeException("contentView: [" + this.contentView + "] is already initialed");
        }
        this.contentView = contentView;
        // 添加背景覆盖层
        fillBackgroundView = new View(getContext());
        fillBackgroundView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fillBackgroundView.setBackgroundColor(Color.WHITE);
        fillBackgroundView.setVisibility(GONE);
        fillBackgroundView.setOnClickListener(this);
        this.addView(fillBackgroundView, Math.max(this.getChildCount() - 1, 0));

        // 添加内容
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ABOVE, onRapidFloatingActionListener.obtainRFAButton().getId());
        lp.addRule(RelativeLayout.ALIGN_RIGHT, onRapidFloatingActionListener.obtainRFAButton().getId());
        this.contentView.setLayoutParams(lp);
        this.contentView.setVisibility(GONE);
        this.addView(this.contentView);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (fillBackgroundView == v) {
            collapseContent();
        }
    }

    private boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void toggleContent() {
        if (isExpanded) {
            collapseContent();
        } else {
            expandContent();
        }
    }

    public void expandContent() {
        isExpanded = true;
        contentAnimator.setTarget(this.contentView);
        contentAnimator.setFloatValues(0.0f, 1.0f);
        contentAnimator.setPropertyName("alpha");

        fillBackgroundAnimator.setTarget(this.fillBackgroundView);
        fillBackgroundAnimator.setFloatValues(0.0f, 0.6f);
        fillBackgroundAnimator.setPropertyName("alpha");

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(contentAnimator, fillBackgroundAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        onRapidFloatingActionListener.onExpandAnimator(animatorSet);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                contentView.setVisibility(VISIBLE);
                fillBackgroundView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                isExpanded = true;
            }
        });

        animatorSet.start();

    }

    public void collapseContent() {
        isExpanded = false;
        contentAnimator.setTarget(this.contentView);
        contentAnimator.setFloatValues(1.0f, 0.0f);
        contentAnimator.setPropertyName("alpha");

        fillBackgroundAnimator.setTarget(this.fillBackgroundView);
        fillBackgroundAnimator.setFloatValues(0.6f, 0.0f);
        fillBackgroundAnimator.setPropertyName("alpha");

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(contentAnimator, fillBackgroundAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        onRapidFloatingActionListener.onExpandAnimator(animatorSet);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                contentView.setVisibility(VISIBLE);
                fillBackgroundView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                contentView.setVisibility(GONE);
                fillBackgroundView.setVisibility(GONE);
            }
        });
        animatorSet.start();

    }

    ObjectAnimator contentAnimator = new ObjectAnimator();
    ObjectAnimator fillBackgroundAnimator = new ObjectAnimator();


}
