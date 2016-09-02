package com.example.sofina.myapplication.render;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by sofina on 2016/9/1.
 */
public abstract class BaseRender {
    private static final long ANIMATION_DURATION = 1000;

    private static final float DEFAULT_WIDTH = 300f;

    private static final float DEFAULT_HEIGHT = 800f;

    private static final float DEFAULT_CENTER_RADIUS = 12.5f;

    private static final float DEFAULT_STROKE_WIDTH = 2.5f;

    protected float mWidth;

    protected float mHeight;

    protected float mStrokeWidth;

    protected float mCenterRadius;

    private long mDuration;

    private Drawable.Callback mCallback;

    private ValueAnimator mAnimator;

    private DisplayMetrics metrics = null;
    private float screenDensity;

    public BaseRender(Context context) {
        setupParams(context);
        setUpAnimators();
    }

    protected void setupParams(Context context) {
        metrics = context.getResources().getDisplayMetrics();
        screenDensity = metrics.density;

        mWidth = screenDensity * DEFAULT_WIDTH;
        mHeight = screenDensity * DEFAULT_HEIGHT;
        mCenterRadius = screenDensity * DEFAULT_CENTER_RADIUS;
        mStrokeWidth = screenDensity * DEFAULT_STROKE_WIDTH;

        mDuration = ANIMATION_DURATION;
    }

    protected void setUpAnimators() {
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setDuration(mDuration);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                computeRender((Float) valueAnimator.getAnimatedValue());
                invalidateSelf();
            }
        });
    }

    public abstract void draw(Canvas canvas, Rect bounds);

    public abstract void computeRender(float process);

    public abstract void setAlpha(int alpha);

    public abstract void setColorFilter(ColorFilter colorFilter);

    public abstract void reset();

    public void start() {
        reset();
        mAnimator.start();
    }

    public void stop() {
        mAnimator.cancel();
    }

    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    public void setCallback(Drawable.Callback callback) {
        mCallback = callback;
    }

    public void invalidateSelf() {
        mCallback.invalidateDrawable(null);
    }

    public void addAnimatorListener(Animator.AnimatorListener listener) {
        mAnimator.addListener(listener);
    }

    public void setCenterRadius(float centerRadius) {
        mCenterRadius = centerRadius * screenDensity;
    }

    public float getCenterRadius() {
        return mCenterRadius;
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth * screenDensity;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        this.mWidth = width * screenDensity;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float height) {
        this.mHeight = height;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
        mAnimator.setDuration(mDuration);
    }

}


