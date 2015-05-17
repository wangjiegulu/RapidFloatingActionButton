package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import android.os.Bundle;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/6/15.
 */
@AILayout(R.layout.card_list_sample)
public class CardListSampleActivity extends AIActionBarActivity implements RapidFloatingActionContentCardListView.OnRapidFloatingActionContentCardListViewListener {

    @AIView(R.id.card_list_sample_rfal)
    private RapidFloatingActionLayout rfaLayout;
    @AIView(R.id.card_list_sample_rfab)
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RapidFloatingActionContentCardListView rfaContent = new RapidFloatingActionContentCardListView(context);
        rfaContent.setOnRapidFloatingActionContentCardListViewListener(this);

        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem().setName("wangjie").setResId(R.mipmap.head_test_a));
        cardItems.add(new CardItem().setName("tiantian").setResId(R.mipmap.head_test_b));
        cardItems.add(new CardItem().setName("wangjiegulu").setResId(R.mipmap.head_test_c));
        cardItems.add(new CardItem().setName("咕噜不爱猫").setResId(R.mipmap.head_test_d));
        rfaContent.setList(cardItems);


        rfaLayout.setIsContentAboveLayout(false);
        rfaLayout.setDisableContentDefaultAnimation(true);

        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();


    }


    @Override
    public void onRFACCardListItemClick(int position) {
        showToastMessage("clicked " + position);
        rfabHelper.toggleContent();
    }
}
