package com.wangjie.rapidfloatingactionbutton.example.rfabgroup;

import com.wangjie.androidinject.annotation.present.AISupportFragment;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
public abstract class BaseFragment extends AISupportFragment {
    public abstract String getRfabIdentificationCode();

    public abstract String getTitle();

    public void onInitialRFAB(RapidFloatingActionButton rfab) {
    }

}
