package com.example.sofina.myapplication.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by sofina on 2016/9/1.
 */
public class WaterDropRender extends BaseRender {

    private String TAG = WaterDropRender.class.getSimpleName();

    private Path mPath;

    private Paint mDropPaint;

    private int mWidth;

    private int mHeight;

    private int centerX;

    private int centerY;

    private float maxLength;

    private float mInterpolatedTime;

    private float radius = 50;

    private float c = radius * 0.551915024494f;

    private float cDistance = c * 0.45f;

    private HorizonPoint p2, p4;

    private VerticalPoint p1, p3;

    public WaterDropRender(Context context) {
        super(context);
        initPaint();
        initParams();
    }

    private void initPaint() {
        mDropPaint = new Paint();
        mDropPaint.setColor(0xff469ae9);
        mDropPaint.setStyle(Paint.Style.FILL);
        mDropPaint.setStrokeWidth(1);
        mDropPaint.setAntiAlias(true);
    }

    private void initParams() {
        mWidth = (int) getWidth();
        mHeight = (int) getHeight();
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        maxLength = mHeight - 2 * radius;

        mPath = new Path();
        p2 = new HorizonPoint();
        p4 = new HorizonPoint();

        p1 = new VerticalPoint();
        p3 = new VerticalPoint();
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        mPath.reset();
        //坐标中心移动到此位置
        canvas.translate(centerX, 2 * radius);

        if (mInterpolatedTime >= 0 && mInterpolatedTime <= 0.5) {
            model1(mInterpolatedTime);
        } else if (mInterpolatedTime > 0.5 && mInterpolatedTime <= 1) {
            model3(mInterpolatedTime);
        }

        float offset = (maxLength / 2) * (mInterpolatedTime - 0.5f);
        offset = offset > 0 ? offset : 0;
        p1.adjustAllY(offset);
        p2.adjustAllY(offset);
        p3.adjustAllY(offset);
        p4.adjustAllY(offset);

        float[] p = new float[2];
        p[0] = p3.x;
        p[1] = p3.y;

        float[] pleft = new float[2];
        pleft[0] = p3.left.x;
        pleft[1] = p3.left.y;

        float[] pright = new float[2];
        pright[0] = p3.right.x;
        pright[1] = p3.right.y;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawPoints(p, paint);
        canvas.drawPoints(pleft, paint);
        canvas.drawPoints(pright, paint);

        mPath.moveTo(p1.x, p1.y);
        mPath.cubicTo(p1.right.x, p1.right.y, p2.bottom.x, p2.bottom.y, p2.x, p2.y);
        mPath.cubicTo(p2.top.x, p2.top.y, p3.right.x, p3.right.y, p3.x, p3.y);
        mPath.cubicTo(p3.left.x, p3.left.y, p4.top.x, p4.top.y, p4.x, p4.y);
        mPath.cubicTo(p4.bottom.x, p4.bottom.y, p1.left.x, p1.left.y, p1.x, p1.y);

        canvas.drawPath(mPath, mDropPaint);

    }

    private void model0() {
        p1.setY(-radius);
        p3.setY(-radius);
        p3.x = p1.x = 0;
        p3.left.x = p1.left.x = -c;
        p3.right.x = p1.right.x = c;
        p3.y = -radius - radius;

        p2.setX(radius);
        p4.setX(-radius);
        p2.y = p4.y = -radius;
        p2.top.y = p4.top.y = -c-radius;
        p2.bottom.y = p4.bottom.y = c-radius;
    }

    private void model1(float time) {//0~0.5
        model0();

        time = (time) * (10f / 5);
        p1.adjustAllY(radius / 2 * time);
        p2.adjustAllY(radius / 2 * time);
        p4.adjustAllY(radius / 2 * time);

        p3.adjustY(-cDistance);
    }

    private void model3(float time) {//0.5~1
        model1(0.5f);
        time = (time - 0.5f) * (10f / 5);

        p1.adjustAllY(radius / 2 * time);
        p2.adjustAllY(radius / 2 * time);
        p4.adjustAllY(radius / 2 * time);
    }


    @Override
    public void computeRender(float process) {
        mInterpolatedTime = process;
    }

    @Override
    public void setAlpha(int alpha) {
        mDropPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mDropPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public void reset() {

    }

    class HorizonPoint {
        public float x;

        public float y;

        public PointF top = new PointF();

        public PointF bottom = new PointF();

        public void setX(float x) {
            this.x = x;
            top.x = x;
            bottom.x = x;
        }

        public void adjustAllY(float offset) {
            this.y += offset;
            top.y += offset;
            bottom.y += offset;
        }
    }

    class VerticalPoint {
        public float x;

        public float y;

        public PointF left = new PointF();

        public PointF right = new PointF();

        public void setY(float y) {
            this.y = y;
            left.y = y;
            right.y = y;
        }

        public void adjustY(float offset) {
            left.y += offset;
            right.y += offset;
        }

        public void adjustAllY(float offset) {
            this.y += offset;
            left.y += offset;
            right.y += offset;
        }
    }

}
