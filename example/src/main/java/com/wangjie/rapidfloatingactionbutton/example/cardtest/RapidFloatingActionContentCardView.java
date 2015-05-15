package com.wangjie.rapidfloatingactionbutton.example.cardtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.contentimpl.viewbase.RapidFloatingActionContentViewBase;
import com.wangjie.rapidfloatingactionbutton.example.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/7/15.
 */
public class RapidFloatingActionContentCardView extends RapidFloatingActionContentViewBase {
    public RapidFloatingActionContentCardView(Context context) {
        super(context);
    }

    public RapidFloatingActionContentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private LinearLayout contentView;


    int size = 4;

    @NonNull
    @Override
    protected View getContentView() {

        contentView = new LinearLayout(getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setBackgroundColor(0xffaabbcc);
//        contentView.setLayoutParams(new LayoutParams(ABTextUtil.dip2px(getContext(), 150), ABTextUtil.dip2px(getContext(), 50) * size));

        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < size; i++) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.content_card_list_item, null);
            contentView.addView(item);
        }

        return contentView;
    }

    @Override
    protected void measureAll() {
        this.measure(
                MeasureSpec.makeMeasureSpec(ABTextUtil.dip2px(getContext(), 150), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(ABTextUtil.dip2px(getContext(), 200), MeasureSpec.EXACTLY)
        );
    }

    private void initialData() {

    }

}
