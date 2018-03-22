package com.wangjie.rapidfloatingactionbutton.contentimpl.labellist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import com.wangjie.rapidfloatingactionbutton.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionContent;
import com.wangjie.rapidfloatingactionbutton.constants.RFABConstants;
import com.wangjie.rapidfloatingactionbutton.constants.RFABSize;
import com.wangjie.rapidfloatingactionbutton.util.RFABImageUtil;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;
import com.wangjie.rapidfloatingactionbutton.util.RFABViewUtil;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonDrawable;
import com.wangjie.rapidfloatingactionbutton.widget.CircleButtonProperties;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/29/15.
 */
public class RapidFloatingActionContentLabelList extends RapidFloatingActionContent implements View.OnClickListener {
    public interface OnRapidFloatingActionContentLabelListListener<T> {
        void onRFACItemLabelClick(int position, RFACLabelItem<T> item);

        void onRFACItemIconClick(int position, RFACLabelItem<T> item);
    }

    private OnRapidFloatingActionContentLabelListListener onRapidFloatingActionContentLabelListListener;

    public void setOnRapidFloatingActionContentLabelListListener(OnRapidFloatingActionContentLabelListListener onRapidFloatingActionContentLabelListListener) {
        this.onRapidFloatingActionContentLabelListListener = onRapidFloatingActionContentLabelListListener;
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
    protected void initInConstructor() {
        rfacItemDrawableSizePx = RFABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_DRAWABLE_SIZE_DP);

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
        if (!RFABTextUtil.isEmpty(items)) {
            this.items = items;
        }
        return this;
    }

    private void refreshItems() {
        if (RFABTextUtil.isEmpty(items)) {
            throw new RuntimeException(this.getClass().getSimpleName() + "[items] can not be empty!");
        }
        contentView.removeAllViews();
        for (int i = 0, size = items.size(); i < size; i++) {
            RFACLabelItem item = items.get(i);
            // 初始化控件，并设置监听事件
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.rfab__content_label_list_item, null);
            View rootView = RFABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_root_view);

            TextView labelTv = RFABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_label_tv);
            ImageView iconIv = RFABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_icon_iv);
            rootView.setOnClickListener(this);
            labelTv.setOnClickListener(this);
            iconIv.setOnClickListener(this);
            rootView.setTag(R.id.rfab__id_content_label_list_item_position, i);
            labelTv.setTag(R.id.rfab__id_content_label_list_item_position, i);
            iconIv.setTag(R.id.rfab__id_content_label_list_item_position, i);

            // 设置item的图片属性
            CircleButtonProperties circleButtonProperties = new CircleButtonProperties()
                    .setStandardSize(RFABSize.MINI)
                    .setShadowColor(iconShadowColor)
                    .setShadowRadius(iconShadowRadius)
                    .setShadowDx(iconShadowDx)
                    .setShadowDy(iconShadowDy);

            // 为了视觉效果，每个item行间距至少是8dp
            int shadowOffsetHalf = circleButtonProperties.getShadowOffsetHalf();
            int minPadding = RFABTextUtil.dip2px(getContext(), 8);
            if (shadowOffsetHalf < minPadding) {
                int deltaPadding = minPadding - shadowOffsetHalf;
                rootView.setPadding(0, deltaPadding, 0, deltaPadding);
            }

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

            RFABViewUtil.setBackgroundDrawable(iconIv, RFABShape.selectorClickSimple(rfacNormalDrawable, rfacPressedDrawable));
            // 设置中间图标的大小
            int padding = RFABTextUtil.dip2px(getContext(), 8) + shadowOffsetHalf;
            iconIv.setPadding(padding, padding, padding, padding);

            // 渲染UI
            String label = item.getLabel();
            if (RFABTextUtil.isEmpty(label)) {
                labelTv.setVisibility(GONE);
            } else {
                if (item.isLabelTextBold()) {
                    labelTv.getPaint().setFakeBoldText(true);
                }
                labelTv.setVisibility(VISIBLE);
                labelTv.setText(label);
                Drawable bgDrawable = item.getLabelBackgroundDrawable();
                if (null != bgDrawable) {
                    RFABViewUtil.setBackgroundDrawable(labelTv, bgDrawable);
                }
                Integer labelColor = item.getLabelColor();
                if (null != labelColor) {
                    labelTv.setTextColor(labelColor);
                }
                Integer labelSize = item.getLabelSizeSp();
                if (null != labelSize) {
                    labelTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
                }

            }

            int resId;
            Drawable drawable = item.getDrawable();
            if (null != drawable) {
                iconIv.setVisibility(VISIBLE);
                drawable.setBounds(0, 0, rfacItemDrawableSizePx, rfacItemDrawableSizePx);
                iconIv.setImageDrawable(drawable);
            } else if ((resId = item.getResId()) > 0) {
                iconIv.setVisibility(VISIBLE);
                iconIv.setImageDrawable(RFABImageUtil.getResourceDrawableBounded(getContext(), resId, rfacItemDrawableSizePx));
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
        if (null == onRapidFloatingActionContentLabelListListener
                ||
                null == (position = (Integer) v.getTag(R.id.rfab__id_content_label_list_item_position))) {
            return;
        }
        int i = v.getId();
        if (i == R.id.rfab__content_label_list_label_tv) {
            onRapidFloatingActionContentLabelListListener.onRFACItemLabelClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_icon_iv) {
            onRapidFloatingActionContentLabelListListener.onRFACItemIconClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_root_view) {
            onRapidFloatingActionListener.collapseContent();
        }
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

    /**
     * ********************** 每个item动画 ************************
     */
    private OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        int count = contentView.getChildCount();
        for (int i = 0; i < count; i++) {
            View rootView = contentView.getChildAt(i);
            ImageView iconIv = RFABViewUtil.obtainView(rootView, R.id.rfab__content_label_list_icon_iv);
            if (null == iconIv) {
                return;
            }
            ObjectAnimator animator = new ObjectAnimator();
            animator.setTarget(iconIv);
            animator.setFloatValues(45f, 0);
            animator.setPropertyName("rotation");
            animator.setInterpolator(mOvershootInterpolator);
            animator.setStartDelay((count * i) * 20);
            animatorSet.playTogether(animator);
        }
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        int count = contentView.getChildCount();
        for (int i = 0; i < count; i++) {
            View rootView = contentView.getChildAt(i);
            ImageView iconIv = RFABViewUtil.obtainView(rootView, R.id.rfab__content_label_list_icon_iv);
            if (null == iconIv) {
                return;
            }
            ObjectAnimator animator = new ObjectAnimator();
            animator.setTarget(iconIv);
            animator.setFloatValues(0, 45f);
            animator.setPropertyName("rotation");
            animator.setInterpolator(mOvershootInterpolator);
            animator.setStartDelay((count * i) * 20);
            animatorSet.playTogether(animator);
        }
    }

}
