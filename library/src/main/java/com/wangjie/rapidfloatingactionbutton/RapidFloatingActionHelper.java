package com.wangjie.rapidfloatingactionbutton;

import android.content.Context;
import android.animation.AnimatorSet;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public final class RapidFloatingActionHelper implements OnRapidFloatingActionListener {
    private Context context;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionContent rfaContent;


    public RapidFloatingActionHelper(Context context, RapidFloatingActionLayout rfaLayout, RapidFloatingActionButton rfaBtn, RapidFloatingActionContent rfaContent) {
        this.context = context;
        this.rfaLayout = rfaLayout;
        this.rfaBtn = rfaBtn;
        this.rfaContent = rfaContent;
    }

    public RapidFloatingActionHelper setRfaLayout(RapidFloatingActionLayout rfaLayout) {
        this.rfaLayout = rfaLayout;
        return this;
    }

    public RapidFloatingActionHelper setRfaButton(RapidFloatingActionButton rfaBtn) {
        this.rfaBtn = rfaBtn;
        return this;
    }

    public RapidFloatingActionHelper setRfaContent(RapidFloatingActionContent rfaContent) {
        this.rfaContent = rfaContent;
        return this;
    }

    public final RapidFloatingActionHelper build() {
        rfaLayout.setOnRapidFloatingActionListener(this);
        rfaBtn.setOnRapidFloatingActionListener(this);
        rfaContent.setOnRapidFloatingActionListener(this);
        rfaLayout.setContentView(rfaContent);
        // 通知content RFABHelper构建完毕
        rfaContent.initAfterRFABHelperBuild();
        return this;
    }

    @Override
    public void onRFABClick() {
        rfaLayout.toggleContent();
    }

    @Override
    public void toggleContent() {
        rfaLayout.toggleContent();
    }

    @Override
    public void expandContent() {
        rfaLayout.expandContent();
    }

    @Override
    public void collapseContent() {
        rfaLayout.collapseContent();
    }

    @Override
    public final RapidFloatingActionLayout obtainRFALayout() {
        return rfaLayout;
    }

    @Override
    public final RapidFloatingActionButton obtainRFAButton() {
        return rfaBtn;
    }

    @Override
    public final RapidFloatingActionContent obtainRFAContent() {
        return rfaContent;
    }

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        rfaContent.onExpandAnimator(animatorSet);
        rfaBtn.onExpandAnimator(animatorSet);
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        rfaContent.onCollapseAnimator(animatorSet);
        rfaBtn.onCollapseAnimator(animatorSet);
    }


}
