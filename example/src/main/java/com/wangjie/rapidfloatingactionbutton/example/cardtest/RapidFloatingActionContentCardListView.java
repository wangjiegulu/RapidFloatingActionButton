package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase.RapidFloatingActionContentViewBase;
import com.wangjie.rapidfloatingactionbutton.example.R;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/7/15.
 */
public class RapidFloatingActionContentCardListView extends RapidFloatingActionContentViewBase implements View.OnClickListener {
    public interface OnRapidFloatingActionContentCardListViewListener {
        void onRFACCardListItemClick(int position);
    }

    private OnRapidFloatingActionContentCardListViewListener onRapidFloatingActionContentCardListViewListener;

    public void setOnRapidFloatingActionContentCardListViewListener(OnRapidFloatingActionContentCardListViewListener onRapidFloatingActionContentCardListViewListener) {
        this.onRapidFloatingActionContentCardListViewListener = onRapidFloatingActionContentCardListViewListener;
    }

    public RapidFloatingActionContentCardListView(Context context) {
        super(context);
    }

    public RapidFloatingActionContentCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentCardListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentCardListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private LinearLayout contentView;

    private List<CardItem> list = new ArrayList<>();

    public void setList(List<CardItem> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    @NonNull
    @Override
    protected View getContentView() {
        contentView = new LinearLayout(getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        ShadowViewHelper.bindShadowHelper(
                new ShadowProperty()
                        .setShadowRadius(ABTextUtil.dip2px(getContext(), 4))
                        .setShadowColor(0x66000000)
                ,
                contentView
        );

        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i = 0, len = list.size(); i < len; i++) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.content_card_list_item, null);
            View rootView = item.findViewById(R.id.content_card_list_item_root_view);
            rootView.setTag(com.wangjie.rapidfloatingactionbutton.R.id.rfab__id_content_label_list_item_position, i);
            ABViewUtil.setBackgroundDrawable(rootView, ABShape.selectorClickColorCornerSimple(Color.WHITE, 0xffF0F0F0, 0));
            rootView.setOnClickListener(this);

            ImageView logoIv = (ImageView) rootView.findViewById(R.id.content_card_list_item_logo_iv);
            TextView titleTv = (TextView) rootView.findViewById(R.id.content_card_list_item_title_tv);
            CardItem cardItem = list.get(i);
            logoIv.setImageResource(cardItem.getResId());
            titleTv.setText(cardItem.getName());

            contentView.addView(item);
        }

        return contentView;
    }


    @Override
    public void onClick(View v) {
        Integer position;
        if (null == onRapidFloatingActionContentCardListViewListener
                ||
                null == (position = (Integer) v.getTag(com.wangjie.rapidfloatingactionbutton.R.id.rfab__id_content_label_list_item_position))) {
            return;
        }
        switch (v.getId()) {
            case R.id.content_card_list_item_root_view:
                onRapidFloatingActionContentCardListViewListener.onRFACCardListItemClick(position);
                break;
        }
    }
}
