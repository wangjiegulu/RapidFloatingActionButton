package com.wangjie.rapidfloatingactionbutton.contentimpl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nineoldandroids.animation.AnimatorSet;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABImageProcess;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.constants.RFABSize;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonDrawable;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonProperties;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionContentLabelList extends RapidFloatingActionContent implements View.OnClickListener {
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

    private int rfacItemDrawableSizePx;

    private LinearLayout contentView;
    private List<RFACLabelItem> items;
    private int iconShadowRadius;
    private int iconShadowColor;
    private int iconShadowDx;
    private int iconShadowDy;

    @Override
    protected void initAfterConstructor() {
        rfacItemDrawableSizePx = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_DRAWABLE_SIZE_DP);

        contentView = new LinearLayout(getContext());
        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        setRootView(contentView);
    }

    @Override
    protected void initAfterRFABHelperBuild() {
        refreshItems();
    }

    public List<RFACLabelItem> getItems() {
        return items;
    }

    public RapidFloatingActionContentLabelList setItems(List<RFACLabelItem> items) {
        if (!ABTextUtil.isEmpty(items)) {
            this.items = items;
        }
        return this;
    }

    private void refreshItems() {
        if (ABTextUtil.isEmpty(items)) {
            throw new RuntimeException(this.getClass().getSimpleName() + "[items] can not be empty!");
        }
        contentView.removeAllViews();
        for (int i = 0, size = items.size(); i < size; i++) {
            RFACLabelItem item = items.get(i);
            // 初始化控件，并设置监听事件
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.rfab__content_label_list_item, null);
            View rootView = itemView.findViewById(R.id.rfab__content_label_list_root_view);

            if (0 >= iconShadowRadius) {
                int padding = ABTextUtil.dip2px(getContext(), 8);
                rootView.setPadding(0, padding, 0, padding);
            }

            TextView labelTv = (TextView) itemView.findViewById(R.id.rfab__content_label_list_label_tv);
            ImageView iconIv = (ImageView) itemView.findViewById(R.id.rfab__content_label_list_icon_iv);
            rootView.setOnClickListener(this);
            labelTv.setOnClickListener(this);
            iconIv.setOnClickListener(this);
            rootView.setTag(i);
            labelTv.setTag(i);
            iconIv.setTag(i);

            // 设置item的图片属性
            CircleButtonProperties circleButtonProperties = new CircleButtonProperties()
                    .setStandardSize(RFABSize.MINI)
                    .setShadowColor(iconShadowColor)
                    .setShadowRadius(iconShadowRadius)
                    .setShadowDx(iconShadowDx)
                    .setShadowDy(iconShadowDy);
            // 设置iconIv的位置（需要与RFAB居中）
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

            Integer normalColor = item.getIconNormalColor();
            Integer pressedColor = item.getIconPressedColor();

            CircleButtonDrawable rfacNormalDrawable = new CircleButtonDrawable(getContext(), circleButtonProperties,
                    null == normalColor ? getResources().getColor(R.color.rfab__color_background_normal) : normalColor);
            CircleButtonDrawable rfacPressedDrawable = new CircleButtonDrawable(getContext(), circleButtonProperties,
                    null == pressedColor ? getResources().getColor(R.color.rfab__color_background_pressed) : pressedColor);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                iconIv.setLayerType(LAYER_TYPE_SOFTWARE, rfacNormalDrawable.getPaint());
            }

            ABViewUtil.setBackgroundDrawable(iconIv, ABShape.selectorClickSimple(rfacNormalDrawable, rfacPressedDrawable));
            // 设置中间图标的大小
            int padding = ABTextUtil.dip2px(getContext(), 8) + circleButtonProperties.getShadowOffsetHalf();
            iconIv.setPadding(padding, padding, padding, padding);

            // 渲染UI
            String label = item.getLabel();
            if (ABTextUtil.isEmpty(label)) {
                labelTv.setVisibility(GONE);
            } else {
                if (item.isLabelTextBold()) {
                    labelTv.getPaint().setFakeBoldText(true);
                }
                labelTv.setVisibility(VISIBLE);
                labelTv.setText(label);
            }
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

            contentView.addView(itemView);
        }

    }

    @Override
    protected void initialContentViews(View rootView) {
    }


    @Override
    public void onClick(View v) {
        Integer position;
        if (null == onRapidFloatingActionContentListener || null == (position = (Integer) v.getTag())) {
            return;
        }
        int i = v.getId();
        if (i == R.id.rfab__content_label_list_label_tv) {
            onRapidFloatingActionContentListener.onRFACItemLabelClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_icon_iv) {
            onRapidFloatingActionContentListener.onRFACItemIconClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_root_view) {
            onRapidFloatingActionListener.toggleContent();
        }
    }

    private boolean shouldExecuteExpandAnimator;
    private boolean shouldExecuteCollapseAnimator;

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {


    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
    }

    public RapidFloatingActionContentLabelList setIconShadowRadius(int iconShadowRadius) {
        this.iconShadowRadius = iconShadowRadius;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowColor(int iconShadowColor) {
        this.iconShadowColor = iconShadowColor;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowDx(int iconShadowDx) {
        this.iconShadowDx = iconShadowDx;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowDy(int iconShadowDy) {
        this.iconShadowDy = iconShadowDy;
        return this;
    }

}
