package com.example.sofina.myapplication.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import com.example.sofina.myapplication.render.BaseRender;

/**
 * Created by sofina on 2016/9/1.
 */
public class LoadingDrawable extends Drawable implements Animatable {

    private BaseRender mRender;

    private Callback mCallback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable drawable) {
            invalidateSelf();
        }

        @Override
        public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {
            scheduleSelf(runnable, l);
        }

        @Override
        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            unscheduleSelf(runnable);
        }
    };

    public LoadingDrawable(BaseRender render) {
        this.mRender = render;
        this.mRender.setCallback(mCallback);
    }

    @Override
    public void draw(Canvas canvas) {
        mRender.draw(canvas, getBounds());
    }

    @Override
    public void setAlpha(int i) {
        mRender.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mRender.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        mRender.start();
    }

    @Override
    public void stop() {
        mRender.stop();
    }

    @Override
    public boolean isRunning() {
        return mRender.isRunning();
    }

    /**
     * The setBounds(Rect) method must be called to tell the Drawable
     * where it is drawn and how large it should be. All Drawables
     * should respect the requested size, often simply by scaling
     * their imagery. A client can find the preferred size for some
     * Drawables with the getIntrinsicHeight() and getIntrinsicWidth() methods.
     */
    @Override
    public int getIntrinsicWidth() {
        return (int) (mRender.getWidth() + 1);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (mRender.getHeight() + 1);
    }
}
