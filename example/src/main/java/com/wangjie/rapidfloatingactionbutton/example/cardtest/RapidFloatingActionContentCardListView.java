package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase.RapidFloatingActionContentViewBase;
import com.wangjie.rapidfloatingactionbutton.example.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/7/15.
 */
public class RapidFloatingActionContentCardListView extends RapidFloatingActionContentViewBase implements View.OnClickListener {
    public interface OnRapidFloatingActionContentCardListViewListener {
        void onRFACItemClick(int position);
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

    int size = 4;

    @NonNull
    @Override
    protected View getContentView() {

        contentView = new LinearLayout(getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
//        contentView.setLayoutParams(new LayoutParams(ABTextUtil.dip2px(getContext(), 150), ABTextUtil.dip2px(getContext(), 50) * size));

        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < size; i++) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.content_card_list_item, null);
            View rootView = item.findViewById(R.id.content_card_list_item_root_view);
            rootView.setTag(com.wangjie.rapidfloatingactionbutton.R.id.rfab__id_content_label_list_item_position, i);
            ABViewUtil.setBackgroundDrawable(rootView, ABShape.selectorClickColorCornerSimple(Color.WHITE, 0xffF0F0F0, 0));
            rootView.setOnClickListener(this);
            // ...

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
                onRapidFloatingActionContentCardListViewListener.onRFACItemClick(position);
                break;
        }
    }
}
