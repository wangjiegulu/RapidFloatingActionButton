package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
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
public class CardListSampleActivity extends AppCompatActivity implements RapidFloatingActionContentCardListView.OnRapidFloatingActionContentCardListViewListener {

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list_sample);
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.card_list_sample_rfal);
        rfaButton = (RapidFloatingActionButton) findViewById(R.id.card_list_sample_rfab);

        RapidFloatingActionContentCardListView rfaContent = new RapidFloatingActionContentCardListView(this);
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
                this,
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();


    }


    @Override
    public void onRFACCardListItemClick(int position) {
        Toast.makeText(this, "clicked " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }
}
