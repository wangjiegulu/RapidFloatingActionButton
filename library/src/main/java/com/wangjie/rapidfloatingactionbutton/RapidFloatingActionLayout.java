package com.wangjie.rapidfloatingactionbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionLayout extends RelativeLayout implements OnClickListener {
    private static final String TAG = RapidFloatingActionLayout.class.getSimpleName();

    Context c;

    String bigButtonLabel = "Label";
    TextView bigButtonLabelTextView;

    public String getBigButtonLabel() {
        return bigButtonLabel;
    }

    public void setBigButtonLabel(String bigButtonLabel) {
        this.bigButtonLabel = bigButtonLabel;
    }

    public RapidFloatingActionLayout(Context context) {
        super(context);
        c = context;
        initAfterConstructor();
    }

    public RapidFloatingActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        parserAttrs(context, attrs, 0, 0);
        initAfterConstructor();
    }

    public RapidFloatingActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        c = context;
        parserAttrs(context, attrs, defStyleAttr, 0);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        c = context;
        parserAttrs(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
    }

    private void parserAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RapidFloatingActionLayout, defStyleAttr, defStyleRes);
        frameColor = a.getColor(R.styleable.RapidFloatingActionLayout_rfal_frame_color, getContext().getResources().getColor(R.color.rfab__color_frame));
        frameAlpha = a.getFloat(R.styleable.RapidFloatingActionLayout_rfal_frame_alpha,
                Float.valueOf(getResources().getString(R.string.rfab_rfal__float_convert_color_alpha))
        );

        frameAlpha = frameAlpha > 1f ? 1f : (frameAlpha < 0f ? 0f : frameAlpha);

        a.recycle();

    }

    public static final long ANIMATION_DURATION = 150/*ms*/;

    private void initAfterConstructor() {

    }

    private OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    private View fillFrameView;
    private RapidFloatingActionContent contentView;

    private int frameColor;
    private float frameAlpha;
    /**
     * 展开的内容RFAC是否显示在RFAB上方，否则就覆盖在RFAB上面，默认true
     */
    private boolean isContentAboveLayout = true;

    public void setIsContentAboveLayout(boolean isContentAboveLayout) {
        this.isContentAboveLayout = isContentAboveLayout;
    }

    public boolean isContentAboveLayout() {
        return isContentAboveLayout;
    }

    /**
     * 是否禁止contentView展开和收缩的默认动画（如果禁用，可以自己实现自定义动画）
     */
    private boolean disableContentDefaultAnimation = false;

    public void setDisableContentDefaultAnimation(boolean disableContentDefaultAnimation) {
        this.disableContentDefaultAnimation = disableContentDefaultAnimation;
    }

    public RapidFloatingActionLayout setContentView(RapidFloatingActionContent contentView) {
        if (null == contentView) {
            throw new RuntimeException("contentView can not be null");
        }
        if (null != this.contentView) {
            this.removeView(this.contentView);
//            throw new RuntimeException("contentView: [" + this.contentView + "] is already initialed");
            Logger.w(TAG, "contentView: [" + this.contentView + "] is already initialed");
        }
        this.contentView = contentView;
        // 添加背景覆盖层
        fillFrameView = new View(getContext());
        fillFrameView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fillFrameView.setBackgroundColor(frameColor);
        fillFrameView.setVisibility(GONE);
        fillFrameView.setOnClickListener(this);
        this.addView(fillFrameView, Math.max(this.getChildCount() - 1, 0));

        // 添加内容
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int rfabId = onRapidFloatingActionListener.obtainRFAButton().getId();
        lp.addRule(RelativeLayout.ABOVE, rfabId);
        lp.addRule(RelativeLayout.ALIGN_RIGHT, rfabId);

        if (!isContentAboveLayout && null != onRapidFloatingActionListener) {
            lp.bottomMargin = -onRapidFloatingActionListener.obtainRFAButton().getRfabProperties().getRealSizePx(getContext());
        }

        this.contentView.setLayoutParams(lp);
        this.contentView.setVisibility(GONE);

        this.addView(this.contentView);
        ViewTreeObserver obs = this.contentView.getViewTreeObserver();
        bigButtonLabelTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.rfab__label_text_view, null);

        bigButtonLabelTextView.setText(bigButtonLabel);
        // When the button is measured
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // IT IS A DIRTY HACK BECAUSE LIB DESIGN DO NOT ALLOW TO DO THAT
                // If view is not set yet
                if (RapidFloatingActionLayout.this.findViewWithTag(TAG) == null) {
                    RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    WindowManager wm = (WindowManager) RapidFloatingActionLayout.this.getContext().getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    // Use of deprecated method to support API 9
                    // TODO: Separate in two and use #getSize() for the newer APIs
                    int width = display.getWidth();  // deprecated
                    int height = display.getHeight();  // deprecated
                    // Before the button
                    // 10% margin should be ok to be aligned with the others.
                    int marginRight = (int) ((width - ViewHelper.getX(RapidFloatingActionLayout.this.onRapidFloatingActionListener.obtainRFAButton())) * 1.1);
                    Log.d(TAG, "marginRight:" + marginRight);
                    params.rightMargin = marginRight;
                    // Middle vertical
                    int marginBottom = (height - (int) ViewHelper.getY(RapidFloatingActionLayout.this.onRapidFloatingActionListener.obtainRFAButton())) / 4;
                    Log.d(TAG, "marginBottom:" + marginBottom);
                    params.bottomMargin = marginBottom;
                    bigButtonLabelTextView.setLayoutParams(params);
                    bigButtonLabelTextView.setTag(TAG);
                    bigButtonLabelTextView.setVisibility(INVISIBLE);
                    RapidFloatingActionLayout.this.addView(bigButtonLabelTextView);
                }
            }
        });


        return this;
    }

    @Override
    public void onClick(View v) {
        if (fillFrameView == v) {
            collapseContent();
        }
    }

    public void setFrameColor(int frameColor) {
        this.frameColor = frameColor;
        if (null != fillFrameView) {
            fillFrameView.setBackgroundColor(frameColor);
        }
    }

    public void setFrameAlpha(float frameAlpha) {
        this.frameAlpha = frameAlpha;
    }

    private boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void toggleContent() {
        if (isExpanded) {
            collapseContent();
            hideLabel();
        } else {
            expandContent();
            showLabel();
        }
    }

    private void showLabel() {
        bigButtonLabelTextView.setVisibility(VISIBLE);

    }

    private void hideLabel() {
        bigButtonLabelTextView.setVisibility(GONE);
    }

    private AnimatorSet animatorSet;

    public void expandContent() {
        if (isExpanded) {
            return;
        }
        endAnimatorSet();
        isExpanded = true;

        fillFrameAnimator.setTarget(this.fillFrameView);
        fillFrameAnimator.setFloatValues(0.0f, frameAlpha);
        fillFrameAnimator.setPropertyName("alpha");

        animatorSet = new AnimatorSet();
        if (disableContentDefaultAnimation) {
            animatorSet.playTogether(fillFrameAnimator);
        } else {
            contentAnimator.setTarget(this.contentView);
            contentAnimator.setFloatValues(0.0f, 1.0f);
            contentAnimator.setPropertyName("alpha");
            animatorSet.playTogether(contentAnimator, fillFrameAnimator);
        }
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(mAccelerateInterpolator);
        onRapidFloatingActionListener.onExpandAnimator(animatorSet);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                contentView.setVisibility(VISIBLE);
                fillFrameView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isExpanded = true;
            }
        });

        animatorSet.start();
    }

    public void collapseContent() {
        if (!isExpanded) {
            return;
        }
        endAnimatorSet();
        isExpanded = false;

        fillFrameAnimator.setTarget(this.fillFrameView);
        fillFrameAnimator.setFloatValues(frameAlpha, 0.0f);
        fillFrameAnimator.setPropertyName("alpha");

        animatorSet = new AnimatorSet();
        if (disableContentDefaultAnimation) {
            animatorSet.playTogether(fillFrameAnimator);
        } else {
            contentAnimator.setTarget(this.contentView);
            contentAnimator.setFloatValues(1.0f, 0.0f);
            contentAnimator.setPropertyName("alpha");
            animatorSet.playTogether(contentAnimator, fillFrameAnimator);
        }
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(mAccelerateInterpolator);
        onRapidFloatingActionListener.onCollapseAnimator(animatorSet);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fillFrameView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fillFrameView.setVisibility(GONE);
                contentView.setVisibility(GONE);
                isExpanded = false;
            }
        });
        animatorSet.start();

    }

    private void endAnimatorSet() {
        if (null != animatorSet) {
            animatorSet.end();
        }
    }

    private ObjectAnimator contentAnimator = new ObjectAnimator();
    private ObjectAnimator fillFrameAnimator = new ObjectAnimator();
    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();

    public RapidFloatingActionContent getContentView() {
        return contentView;
    }
}
