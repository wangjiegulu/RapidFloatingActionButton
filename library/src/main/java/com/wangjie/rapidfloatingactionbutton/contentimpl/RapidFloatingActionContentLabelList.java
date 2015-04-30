package com.wangjie.rapidfloatingactionbutton.contentimpl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;
import com.wangjie.androidbucket.adapter.ABaseAdapter;
import com.wangjie.androidbucket.adapter.listener.OnConvertViewClickListener;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionContentLabelList extends RapidFloatingActionContent {
    public interface OnRapidFloatingActionContentListener<T> {
        void onRFACItemLabelClick(int position, RFACLabelItem<T> item);

        void onRFACItemIconClick(int position, RFACLabelItem<T> item);
    }

    private OnRapidFloatingActionContentListener onRapidFloatingActionContentListener;

    public void setOnRapidFloatingActionContentListener(OnRapidFloatingActionContentListener onRapidFloatingActionContentListener) {
        this.onRapidFloatingActionContentListener = onRapidFloatingActionContentListener;
    }

    public RapidFloatingActionContentLabelList(Context context) {
        super(context);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ListView contentLv;
    private LabelAdapter adapter;

    @Override
    protected void init() {
        contentLv = new ListView(getContext());
        contentLv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentLv.setDividerHeight(0);
        contentLv.setDivider(null);
        contentLv.setVerticalScrollBarEnabled(false);
        setRootView(contentLv);
    }

    public List<RFACLabelItem> getItems() {
        return adapter.items;
    }

    public void setItems(List<RFACLabelItem> items) {
        adapter.items = items;
    }

    @Override
    protected void initialContentViews(View rootView) {
        adapter = new LabelAdapter(contentLv);
        contentLv.setAdapter(adapter);
    }


    private boolean shouldExecuteExpandAnimator;
    private boolean shouldExecuteCollapseAnimator;

    class LabelAdapter extends ABaseAdapter {

        private int rfacItemSizePx;
        private int rfacItemDrawableSizePx;

        public List<RFACLabelItem> items = new ArrayList<>();

        public LabelAdapter(AbsListView listView) {
            super(listView);
            rfacItemSizePx = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_SIZE_DP);
            rfacItemDrawableSizePx = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_DRAWABLE_SIZE_DP);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.rfab__content_label_list_item, null);
                OnConvertViewClickListener onConvertViewClickListener = new OnConvertViewClickListener(convertView, R.id.ab__id_adapter_item_position) {
                    @Override
                    public void onClickCallBack(View registedView, int... positionIds) {
                        if (null == onRapidFloatingActionContentListener || !ABTextUtil.isLeast(positionIds, 1)) {
                            return;
                        }
                        int i = registedView.getId();
                        if (i == R.id.rfab__content_label_list_label_tv) {
                            onRapidFloatingActionContentListener.onRFACItemLabelClick(positionIds[0], items.get(positionIds[0]));
                        } else if (i == R.id.rfab__content_label_list_icon_iv) {
                            onRapidFloatingActionContentListener.onRFACItemIconClick(positionIds[0], items.get(positionIds[0]));
                        } else if (i == R.id.rfab__content_label_list_root_view) {
                            onRapidFloatingActionListener.toggleContent();
                        }
                    }
                };

                ABViewUtil.obtainView(convertView, R.id.rfab__content_label_list_label_tv).setOnClickListener(onConvertViewClickListener);
                ABViewUtil.obtainView(convertView, R.id.rfab__content_label_list_root_view).setOnClickListener(onConvertViewClickListener);
                ImageView iconIv = ABViewUtil.obtainView(convertView, R.id.rfab__content_label_list_icon_iv);
                iconIv.setOnClickListener(onConvertViewClickListener);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iconIv.getLayoutParams();
                if (null == lp) {
                    lp = new LinearLayout.LayoutParams(rfacItemSizePx, rfacItemSizePx);
                }
                lp.width = rfacItemSizePx;
                lp.height = rfacItemSizePx;
                iconIv.setLayoutParams(lp);
                int padding = ABTextUtil.dip2px(getContext(), 8);
                iconIv.setPadding(padding, padding, padding, padding);
                ABViewUtil.setBackgroundDrawable(iconIv, ABShape.selectorClickColorCornerSimple(Color.WHITE, getContext().getResources().getColor(R.color.rfab__color_background_pressed), rfacItemSizePx / 2));

            }
            convertView.setTag(R.id.ab__id_adapter_item_position, position);

            RFACLabelItem item = items.get(position);
            TextView labelTv = ABViewUtil.obtainView(convertView, R.id.rfab__content_label_list_label_tv);
            final ImageView iconIv = ABViewUtil.obtainView(convertView, R.id.rfab__content_label_list_icon_iv);
            labelTv.setText(item.getLabel());
            int resId = item.getResId();
            if (resId > 0) {
                iconIv.setVisibility(VISIBLE);
                iconIv.setImageResource(resId);
                item.setDrawable(ABImageProcess.getResourceDrawableBounded(getContext(), resId, rfacItemDrawableSizePx));

                Drawable drawable = item.getDrawable();
                if (null == drawable) {
                    drawable = ABImageProcess.getResourceDrawableBounded(getContext(), resId, rfacItemDrawableSizePx);
                    item.setDrawable(drawable);
                }
                iconIv.setImageDrawable(drawable);

            } else {
                iconIv.setVisibility(GONE);
            }

            if (shouldExecuteExpandAnimator) {
                int itemSize = rfacItemSizePx;
                ValueAnimator expandSizeAnimator = ValueAnimator.ofInt(itemSize / 2, itemSize);
                expandSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iconIv.getLayoutParams();
                        if (null == lp) {
                            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                        lp.width = value;
                        lp.height = value;
                        iconIv.setLayoutParams(lp);
                    }
                });
//                expandSizeAnimator.setDuration(200);
//                expandSizeAnimator.setInterpolator(new AccelerateInterpolator());
//                expandSizeAnimator.start();
                animatorSet.playTogether(expandSizeAnimator);

            } else if (shouldExecuteCollapseAnimator) {
                int itemSize = rfacItemSizePx;
                ValueAnimator collapseSizeAnimator = ValueAnimator.ofInt(itemSize, itemSize / 2);

                collapseSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iconIv.getLayoutParams();
                        if (null == lp) {
                            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                        lp.width = value;
                        lp.height = value;
                        iconIv.setLayoutParams(lp);
                    }
                });
//                collapseSizeAnimator.setDuration(200);
//                collapseSizeAnimator.setInterpolator(new AccelerateInterpolator());
//                collapseSizeAnimator.start();
                animatorSet.playTogether(collapseSizeAnimator);
            }

            return convertView;
        }
    }

    private AnimatorSet animatorSet;

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        this.animatorSet = animatorSet;
        shouldExecuteExpandAnimator = true;
        shouldExecuteCollapseAnimator = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        this.animatorSet = animatorSet;
        shouldExecuteCollapseAnimator = true;
        shouldExecuteExpandAnimator = false;
        adapter.notifyDataSetChanged();
    }


}
