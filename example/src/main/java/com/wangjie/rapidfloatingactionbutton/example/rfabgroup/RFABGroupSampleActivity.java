package com.wangjie.rapidfloatingactionbutton.example.rfabgroup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.rapidfloatingactionbutton.example.R;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingButtonGroupListener;
import com.wangjie.rapidfloatingactionbutton.rfabgroup.RapidFloatingActionButtonGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
@AILayout(R.layout.rfab_group_sample)
public class RFABGroupSampleActivity extends AIActionBarActivity implements OnRapidFloatingButtonGroupListener {
    @AIView(R.id.rfab_group_sample_pts)
    private PagerTabStrip pts;
    @AIView(R.id.rfab_group_sample_vp)
    private ViewPager pager;
    @AIView(R.id.rfab_group_sample_rfabg)
    private RapidFloatingActionButtonGroup rfabGroup;

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rfabGroup.setOnRapidFloatingButtonGroupListener(this);

        pts.setTabIndicatorColor(Color.RED);
        pts.setTextColor(0xff3f51b5);

        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());

        pager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                rfabGroup.setSection(position);
            }
        });
        pager.setOffscreenPageLimit(3);

    }

    @Override
    public void onRFABGPrepared(RapidFloatingActionButtonGroup rapidFloatingActionButtonGroup) {
        for (BaseFragment fragment : fragments) {
            fragment.onInitialRFAB(rapidFloatingActionButtonGroup.getRFABByIdentificationCode(fragment.getRfabIdentificationCode()));
        }
    }


    class MyPageAdapter extends FragmentStatePagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getTitle();
        }
    }


}
