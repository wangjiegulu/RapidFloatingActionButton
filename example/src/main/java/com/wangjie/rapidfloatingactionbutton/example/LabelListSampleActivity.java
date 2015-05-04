package com.wangjie.rapidfloatingactionbutton.example;

import android.os.Bundle;
import android.widget.Toast;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
@AILayout(R.layout.label_list_sample)
public class LabelListSampleActivity extends AIActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentListener {
    @AIView(R.id.label_list_sample_rfal)
    private RapidFloatingActionLayout rfaLayout;
    @AIView(R.id.label_list_sample_rfab)
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Github: wangjiegulu")
                        .setResId(R.drawable.ic_launcher)
                        .setIconNormalColor(0xffd84315)
                        .setIconPressedColor(0xffbf360c)
                        .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("tiantian.china.2@gmail.com")
                        .setResId(R.drawable.ic_launcher)
                        .setIconNormalColor(0xff4e342e)
                        .setIconPressedColor(0xff3e2723)
                        .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("WangJie")
                        .setResId(R.drawable.ic_launcher)
                        .setIconNormalColor(0xff056f00)
                        .setIconPressedColor(0xff0d5302)
                        .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Compose")
                        .setResId(R.drawable.ic_launcher)
                        .setIconNormalColor(0xff283593)
                        .setIconPressedColor(0xff1a237e)
                        .setWrapper(3)
        );
        rfaContent
                .setItems(items)
//                .setIconShadowRadius(ABTextUtil.dip2px(context, 5))
                .setIconShadowRadius(ABTextUtil.dip2px(context, 5))
//                .setIconShadowColor(0xff999999)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(context, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked icon: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }
}
