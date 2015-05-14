package com.wangjie.rapidfloatingactionbutton.widget;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.wangjie.androidbucket.utils.ABIOUtil;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/7/15.
 */
public class ViewAnimationDrawable extends Drawable {

    public interface OnViewAnimationDrawableListener {
        void onAnimationDrawableOpenEnd();

        void onAnimationDrawableCloseEnd();

        void onAnimationDrawableOpenStart();

        void onAnimationDrawableCloseStart();
    }

    private static final int DURATION_DEFAULT = 300/*ms*/;
    private View view;

    private OnViewAnimationDrawableListener onViewAnimationDrawableListener;

    public void setOnViewAnimationDrawableListener(OnViewAnimationDrawableListener onViewAnimationDrawableListener) {
        this.onViewAnimationDrawableListener = onViewAnimationDrawableListener;
    }

    private int width;
    private int height;

    private Paint paint;
    private Bitmap viewBitmap;

    private int radius;
    private int currentRadius;

    private ValueAnimator animator = new ValueAnimator();

    private PorterDuffXfermode mProPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public ViewAnimationDrawable(View view) {
        this.view = view;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        animator.addUpdateListener(animatorUpdateListener);
        animator.setInterpolator(new DecelerateInterpolator());

    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if ((width = bounds.right - bounds.left) > 0 && (height = bounds.bottom - bounds.top) > 0) {
            radius = (int) Math.sqrt((width * width + height * height));
            currentRadius = radius;

            if (null == viewBitmap) {
//                Bitmap bm = convertViewToBitmap(view);
//                viewBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
                viewBitmap = convertViewToBitmapWithDraw(view, width, height);
//                ABFileUtil.saveBitmap2SD("", "hello2.jpg", viewBitmap);
            }

            invalidateSelf();
        }
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);
        paint.setXfermode(null);
        canvas.drawCircle(width, height, currentRadius, paint);
//        canvas.drawCircle(width, height, radius / 2, paint);
        paint.setXfermode(mProPorterDuffXfermode);
        canvas.drawBitmap(viewBitmap, 0, 0, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
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
            invalidateSelf();
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


    public static Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static Bitmap convertViewToBitmapWithDraw(View view, int width, int height) {
        Bitmap viewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(viewBitmap));
        return viewBitmap;
    }

}
