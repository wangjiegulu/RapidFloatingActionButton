package com.wangjie.rapidfloatingactionbutton.example.rfabgroup;

import android.support.v4.app.Fragment;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
public abstract class BaseFragment extends Fragment {
    public abstract String getRfabIdentificationCode();

    public abstract String getTitle();

    public void onInitialRFAB(RapidFloatingActionButton rfab) {
    }

}
