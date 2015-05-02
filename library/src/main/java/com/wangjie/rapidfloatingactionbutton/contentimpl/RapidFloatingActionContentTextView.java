package com.wangjie.rapidfloatingactionbutton.contentimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.wangjie.rapidfloatingactionbutton.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionContentTextView extends RapidFloatingActionContent {
    public RapidFloatingActionContentTextView(Context context) {
        super(context);
    }

    public RapidFloatingActionContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAfterConstructor() {
        setRootView(R.layout.rfab__content_text);
    }

    @Override
    protected void initialContentViews(View rootView) {
        TextView textTv = (TextView) rootView.findViewById(R.id.rfab_content_text_tv);

        textTv.setText("wahahahahahaha!!!");


    }

}
