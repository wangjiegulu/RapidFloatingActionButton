package com.wangjie.rapidfloatingactionbutton;

import android.content.Context;
import com.nineoldandroids.animation.AnimatorSet;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public final class RapidFloatingActionButtonHelper implements OnRapidFloatingActionListener {
    private Context context;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionContent rfaContent;


    public RapidFloatingActionButtonHelper(Context context, RapidFloatingActionLayout rfaLayout, RapidFloatingActionButton rfaBtn, RapidFloatingActionContent rfaContent) {
        this.context = context;
        this.rfaLayout = rfaLayout;
        this.rfaBtn = rfaBtn;
        this.rfaContent = rfaContent;
    }


    public final RapidFloatingActionButtonHelper build() {
        rfaLayout.setOnRapidFloatingActionListener(this);
        rfaBtn.setOnRapidFloatingActionListener(this);
        rfaContent.setOnRapidFloatingActionListener(this);
        rfaLayout.setContentView(rfaContent);
        // 通知content RFABHelper构建完毕
        rfaContent.initAfterRFABHelperBuild();
        return this;
    }

    @Override
    public void onFABClick() {
        rfaLayout.toggleContent();
    }

    @Override
    public void toggleContent() {
        rfaLayout.toggleContent();
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
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        rfaContent.onCollapseAnimator(animatorSet);
    }

    @Override
    public int getRFABSize() {
        return 0;
    }


}
