package com.wangjie.rapidfloatingactionbutton.rfabgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingButtonGroupListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
public class RapidFloatingActionButtonGroup extends FrameLayout implements View.OnClickListener {
    private static final String TAG = RapidFloatingActionButtonGroup.class.getSimpleName();
    private static final long SWITCH_ANIMATION_DEFAULT_DURATION = 280/*ms*/;

    public RapidFloatingActionButtonGroup(Context context) {
        super(context);
        initAfterConstructor();
    }

    public RapidFloatingActionButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAfterConstructor();
    }

    public RapidFloatingActionButtonGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionButtonGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
    }

    private OnRapidFloatingButtonGroupListener onRapidFloatingButtonGroupListener;

    public void setOnRapidFloatingButtonGroupListener(OnRapidFloatingButtonGroupListener onRapidFloatingButtonGroupListener) {
        this.onRapidFloatingButtonGroupListener = onRapidFloatingButtonGroupListener;
    }

    private ScaleAnimation selectAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private ScaleAnimation selectCenterAnimation = new ScaleAnimation(1f, 1f, 0.3f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private ScaleAnimation notSelectAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    private DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();

    /**
     * 所有的RFAB
     */
    private List<RapidFloatingActionButton> allRfabs = new ArrayList<>();
    private HashMap<String, RapidFloatingActionButton> allRfabsByIdentificationCode = new HashMap<>();
    public static final int NO_SELECTED = -1;
    /**
     * 当前选中的RFAB
     */
    private int currentSelectedIndex = NO_SELECTED;

    private void initAfterConstructor() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w > 0 && h > 0){
            reset();
            int count = this.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = this.getChildAt(i);
                if (!(child instanceof RapidFloatingActionButton)) {
                    continue;
                }
                addRFABToMemory((RapidFloatingActionButton) child);
            }
            if (count > 0) {
                setSection(0, 0);
            }
            // 通知初始化完毕
            if (null != onRapidFloatingButtonGroupListener) {
                onRapidFloatingButtonGroupListener.onRFABGPrepared(this);
            }
        }
    }

    private void reset() {
        allRfabs.clear();
        allRfabsByIdentificationCode.clear();
    }

    public void addRFABs(RapidFloatingActionButton... rfabs) {
        for (RapidFloatingActionButton rfab : rfabs) {
            addRFABToMemory(rfab);
            this.addView(rfab);
        }
    }

    private void addRFABToMemory(@NonNull RapidFloatingActionButton rfab) {
        allRfabs.add(rfab);
        String identificationCode = rfab.getIdentificationCode();
        if (RapidFloatingActionButton.IDENTIFICATION_CODE_NONE.equals(identificationCode)) {
            throw new RuntimeException("RFAB[" + rfab + "]'s IDENTIFICATION CODE can not be IDENTIFICATION_CODE_NONE if you used RapidFloatingActionButtonGroup");
        }
        if (allRfabsByIdentificationCode.containsKey(identificationCode)) {
            throw new RuntimeException("RFAB[" + rfab + "] Duplicate IDENTIFICATION CODE");
        }
        allRfabsByIdentificationCode.put(identificationCode, rfab);
    }

    /**
     * 根据identificationCode获取
     *
     * @param identificationCode
     * @return
     */
    public RapidFloatingActionButton getRFABByIdentificationCode(String identificationCode) {
        return allRfabsByIdentificationCode.get(identificationCode);
    }

    /**
     * 切换到expectIndex
     *
     * @param expectIndex
     */
    public void setSection(int expectIndex) {
        setSection(expectIndex, SWITCH_ANIMATION_DEFAULT_DURATION);
    }

    public void setSection(final int expectIndex, long duration) {
        if (expectIndex < 0 || expectIndex >= allRfabs.size()) {
            return;
        }
        if (currentSelectedIndex == expectIndex) {
            return;
        }
        executeSwitchWithAnimation(expectIndex, duration);
    }


    @Override
    public void onClick(View v) {

    }


    /**
     * 执行切换动画
     *
     * @param expectIndex
     * @param duration
     */
    private void executeSwitchWithAnimation(final int expectIndex, long duration) {
        boolean shouldPlaySelectAnimation = false;
        boolean shouldPlayNotSelectAnimation = false;
        long perDuration = duration / 2;
        for (int i = 0, size = allRfabs.size(); i < size; i++) {
            final RapidFloatingActionButton rfab = allRfabs.get(i);
            rfab.setVisibility(INVISIBLE);
            shouldPlaySelectAnimation = true;
            if (i == expectIndex) { // 为期待选中的rfab设置显示动画
                selectAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        rfab.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rfab.setVisibility(VISIBLE);
                        rfab.clearAnimation();
                        currentSelectedIndex = expectIndex;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                selectAnimation.setInterpolator(mDecelerateInterpolator);
                selectAnimation.setDuration(perDuration);
                rfab.setAnimation(selectAnimation);

                selectCenterAnimation.setInterpolator(mDecelerateInterpolator);
                selectCenterAnimation.setDuration(perDuration);
                final ImageView centerDrawableIv = rfab.getCenterDrawableIv();
                selectCenterAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        centerDrawableIv.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                centerDrawableIv.setAnimation(selectCenterAnimation);

            } else if (i == currentSelectedIndex) { // 为当前选中的rfab设置消失动画
                rfab.setVisibility(VISIBLE);
                shouldPlayNotSelectAnimation = true;
                notSelectAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        rfab.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rfab.setVisibility(INVISIBLE);
                        rfab.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                notSelectAnimation.setInterpolator(mAccelerateInterpolator);
                notSelectAnimation.setDuration(perDuration);
                rfab.setAnimation(notSelectAnimation);
            }
        }

        if (!shouldPlaySelectAnimation) {
            return;
        }
        if (!shouldPlayNotSelectAnimation) {
            selectAnimation.start();
            selectCenterAnimation.setStartOffset(perDuration);
            selectCenterAnimation.start();
        } else {
            selectAnimation.setStartOffset(perDuration);
            notSelectAnimation.start();
            selectAnimation.start();

            selectCenterAnimation.setStartOffset(perDuration * 2);
            selectCenterAnimation.start();

        }
    }


}
