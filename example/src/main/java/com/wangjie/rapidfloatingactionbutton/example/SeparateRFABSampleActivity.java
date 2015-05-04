package com.wangjie.rapidfloatingactionbutton.example;

import android.os.Bundle;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingButtonSeparateListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
@AILayout(R.layout.separate_rfab_sample)
public class SeparateRFABSampleActivity extends AIActionBarActivity implements OnRapidFloatingButtonSeparateListener {
    @AIView(R.id.separate_rfab_sample_rfab)
    private RapidFloatingActionButton rfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rfab.setOnRapidFloatingButtonSeparateListener(this);
    }

    @Override
    public void onRFABClick() {
        showToastMessage("RFAB clicked");
    }
}
