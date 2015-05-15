package com.wangjie.rapidfloatingactionbutton.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.wangjie.androidbucket.utils.ABFileUtil;
import com.wangjie.androidbucket.utils.ABIOUtil;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/15/15.
 */
public class AnimationView extends View {

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

    private void init() {
        this.setBackgroundColor(Color.TRANSPARENT);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        animator.addUpdateListener(animatorUpdateListener);
        animator.setInterpolator(new DecelerateInterpolator());
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


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

//    public void initialDraw() {
//        width = this.getMeasuredWidth();
//        height = this.getMeasuredHeight();
//        radius = (int) Math.sqrt((width * width + height * height));
//        currentRadius = radius;
//
//        if (null == viewBitmap) {
//                Bitmap bm = convertViewToBitmap(drawView);
//                viewBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
////            viewBitmap = convertViewToBitmapWithDraw(drawView, width, height);
//            ABFileUtil.saveBitmap2SD("", "hello4.jpg", viewBitmap);
//        }
//
//        invalidate();
//    }

    @Override
    public void draw(Canvas canvas) {
        if (null == viewBitmap) {
            width = this.getMeasuredWidth();
            height = this.getMeasuredHeight();
            radius = (int) Math.sqrt((width * width + height * height));
            currentRadius = radius;

            Bitmap bm = convertViewToBitmap(drawView);
            viewBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
//            viewBitmap = convertViewToBitmapWithDraw(drawView, width, height);
            ABFileUtil.saveBitmap2SD("", "hello4.jpg", viewBitmap);
        }

        canvas.drawColor(Color.TRANSPARENT);
        paint.setXfermode(null);
        canvas.drawCircle(width, height, currentRadius, paint);
//        canvas.drawCircle(width, height, radius / 2, paint);
        paint.setXfermode(mProPorterDuffXfermode);
        canvas.drawBitmap(viewBitmap, 0, 0, paint);
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
        animator.setIntValues(0, radius);
        animator.setDuration(duration);
        animator.addListener(openAnimatorListenerAdapter);
        return animator;
    }

    public ValueAnimator getCloseAnimator() {
        return getCloseAnimator(DURATION_DEFAULT);
    }

    public ValueAnimator getCloseAnimator(long duration) {
        animator.removeAllListeners();
        animator.setIntValues(radius, 0);
        animator.setDuration(duration);
        animator.addListener(closeAnimatorListenerAdapter);
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
            if (null != onViewAnimationDrawableListener) {
                onViewAnimationDrawableListener.onAnimationDrawableCloseEnd();
            }
        }
    };

    public void recycle() {
        ABIOUtil.recycleBitmap(viewBitmap);
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
}
