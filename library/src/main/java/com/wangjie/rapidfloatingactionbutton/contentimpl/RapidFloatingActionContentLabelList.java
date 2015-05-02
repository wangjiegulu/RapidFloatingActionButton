package com.wangjie.rapidfloatingactionbutton.contentimpl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nineoldandroids.animation.AnimatorSet;
import com.wangjie.androidbucket.adapter.ABaseAdapter;
import com.wangjie.androidbucket.adapter.listener.OnConvertViewClickListener;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.rapidfloatingactionbutton.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.constants.RFABSize;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonDrawable;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonProperties;

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

        //        private int rfacItemSizePx;
        private int rfacItemDrawableSizePx;

        public List<RFACLabelItem> items = new ArrayList<>();

        public LabelAdapter(AbsListView listView) {
            super(listView);
//            rfacItemSizePx = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_SIZE_DP);
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

                CircleButtonProperties circleButtonProperties = new CircleButtonProperties()
                        .setStandardSize(RFABSize.MINI)
                        .setShadowColor(0xffaaaaaa)
                        .setShadowRadius(ABTextUtil.dip2px(getContext(), 4))
                        .setShadowDy(ABTextUtil.dip2px(getContext(), 5));

                int realItemSize = circleButtonProperties.getRealSizePx(getContext());

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iconIv.getLayoutParams();
                if (null == lp) {
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                int rfabRealSize = onRapidFloatingActionListener.obtainRFAButton().getRfabProperties().getRealSizePx(getContext());
                lp.rightMargin = (rfabRealSize - realItemSize) / 2;
                lp.width = realItemSize;
                lp.height = realItemSize;
                iconIv.setLayoutParams(lp);
//                ABViewUtil.setBackgroundDrawable(iconIv, ABShape.selectorClickColorCornerSimple(Color.WHITE, getContext().getResources().getColor(R.color.rfab__color_background_pressed), rfacItemSizePx / 2));

                CircleButtonDrawable rfacDrawable = new CircleButtonDrawable(getContext(),
                        circleButtonProperties
                        ,
                        Color.WHITE
                );

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    iconIv.setLayerType(LAYER_TYPE_SOFTWARE, rfacDrawable.getPaint());
                }

                ABViewUtil.setBackgroundDrawable(iconIv, rfacDrawable);

                int padding = ABTextUtil.dip2px(getContext(), 8) + circleButtonProperties.getShadowOffsetHalf();
                iconIv.setPadding(padding, padding, padding, padding);


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


            return convertView;
        }
    }

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
    }


}
