package com.wangjie.rapidfloatingactionbutton.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import com.wangjie.rapidfloatingactionbutton.util.RFABIOUtil;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/15/15.
 */
public class AnimationView extends View {

    private static final String TAG = AnimationView.class.getSimpleName();

    public interface OnViewAnimationDrawableListener {
        void onAnimationDrawableOpenEnd();

        void onAnimationDrawableCloseEnd();

        void onAnimationDrawableOpenStart();

        void onAnimationDrawableCloseStart();
    }

    private OnViewAnimationDrawableListener onViewAnimationDrawableListener;

    public void setOnViewAnimationDrawableListener(OnViewAnimationDrawableListener onViewAnimationDrawableListener) {
        this.onViewAnimationDrawableListener = onViewAnimationDrawableListener;
    }


    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private DecelerateInterpolator openInterpolator = new DecelerateInterpolator(0.6f);
    private DecelerateInterpolator closeInterpolator = new DecelerateInterpolator(1.8f);

    private void init() {
        this.setBackgroundColor(Color.TRANSPARENT);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        animator.addUpdateListener(animatorUpdateListener);
//        animator.setInterpolator(new DecelerateInterpolator(1.5f));

    }

    private static final int DURATION_DEFAULT = 300/*ms*/;
    private View drawView;

    public void setDrawView(View drawView) {
        this.drawView = drawView;
    }

    private int width;
    private int height;

    private Paint paint;
    private Bitmap viewBitmap;

    private int radius;
    private int currentRadius;

    private ValueAnimator animator = new ValueAnimator();

    private PorterDuffXfermode mProPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private int minRadius = 0;

    public void setMinRadius(int minRadius) {
        this.minRadius = minRadius;
    }

    public void initialDraw() {
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        radius = (int) Math.sqrt((width * width + height * height));
        currentRadius = radius;

        generateViewBitmap();

        invalidate();
    }

    private void generateViewBitmap() {
        if (null == viewBitmap) {
            Bitmap bm = convertViewToBitmap(drawView);
            if (null == bm) {
                return;
            }
            viewBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
//            viewBitmap = convertViewToBitmapWithDraw(drawView, width, height);
//            ABFileUtil.saveBitmap2SD("", "hello4.jpg", viewBitmap);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        generateViewBitmap();

        canvas.drawColor(Color.TRANSPARENT);
        paint.setXfermode(null);
        canvas.drawCircle(width - minRadius, height - minRadius, currentRadius, paint);
        paint.setXfermode(mProPorterDuffXfermode);
        if (null != viewBitmap) {
            canvas.drawBitmap(viewBitmap, 0, 0, paint);
        }
    }

    public void startOpenAnimation() {
        startOpenAnimation(DURATION_DEFAULT);
    }

    public void startOpenAnimation(long duration) {
        getOpenAnimator(duration).start();
    }

    public void startCloseAnimation() {
        startCloseAnimation(DURATION_DEFAULT);
    }

    public void startCloseAnimation(long duration) {
        getCloseAnimator(duration).start();
    }

    public ValueAnimator getOpenAnimator() {
        return getOpenAnimator(DURATION_DEFAULT);
    }

    public ValueAnimator getOpenAnimator(long duration) {
        animator.removeAllListeners();
        animator.setIntValues(minRadius, radius);
        animator.setDuration(duration);
        animator.addListener(openAnimatorListenerAdapter);
        animator.setInterpolator(openInterpolator);

        return animator;
    }

    public ValueAnimator getCloseAnimator() {
        return getCloseAnimator(DURATION_DEFAULT);
    }

    public ValueAnimator getCloseAnimator(long duration) {
        animator.removeAllListeners();
        animator.setIntValues(radius, minRadius);
        animator.setDuration(duration);
        animator.addListener(closeAnimatorListenerAdapter);
        animator.setInterpolator(closeInterpolator);

        return animator;
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            currentRadius = (int) animation.getAnimatedValue();
            invalidate();
        }
    };

    private AnimatorListenerAdapter openAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            if (null != onViewAnimationDrawableListener) {
                onViewAnimationDrawableListener.onAnimationDrawableOpenStart();
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            AnimationView.this.clearAnimation();
            if (null != onViewAnimationDrawableListener) {
                onViewAnimationDrawableListener.onAnimationDrawableOpenEnd();
            }
        }
    };
    private AnimatorListenerAdapter closeAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            if (null != onViewAnimationDrawableListener) {
                onViewAnimationDrawableListener.onAnimationDrawableCloseStart();
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            AnimationView.this.clearAnimation();
            if (null != onViewAnimationDrawableListener) {
                onViewAnimationDrawableListener.onAnimationDrawableCloseEnd();
            }
        }
    };

    public void recycle() {
        RFABIOUtil.recycleBitmap(viewBitmap);
    }


    public Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static Bitmap convertViewToBitmapWithDraw(View view, int width, int height) {
        Bitmap viewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(viewBitmap));
        return viewBitmap;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recycle();
    }
}
