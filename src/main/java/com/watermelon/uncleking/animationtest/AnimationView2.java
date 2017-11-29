package com.watermelon.uncleking.animationtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by UncleKing on 2017/11/28.
 */

public class AnimationView2 extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private Paint mPaint2;
    private RectF mOval;
    private Path mPath;
    private ArrayList<View> mViews = new ArrayList<>();
    private final SurfaceHolder mSurfaceHolder;
    private DrawThread mDrawThread;

    float offset = 0;
    float offsetTwo = -270;
    boolean isRun = true;
    Canvas canvas = null;
    private ValueAnimator mValueAnimator2;
    private ValueAnimator mValueAnimator;
    private Handler mHandler = new Handler();
    private RectF mRectF;
    private float mDuihaoOneEndPointX;
    private ValueAnimator mValueAnimator3;
    private ValueAnimator mValueAnimator4;
    private float mDuihaoOneEndPointY;
    private ValueAnimator mValueAnimator5;
    private ValueAnimator mValueAnimator6;
    private float mDuihaoTwoEndPointX;
    private float mDuihaoTwoEndPointY;


    public AnimationView2(Context context) {
        this(context, null, 0);
    }

    public AnimationView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setZOrderOnTop(true);
        /**设置画布  背景透明*/
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLUE);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(4);
        mPaint2.setColor(Color.parseColor("#FAFAFA"));

        mPath = new Path();
    }


    public void addElement(View view) {
        mViews.add(view);
    }

    public void start() {

        float duihaoOneStartPointX = mRectF.left;
        float duihaoOneStartPointY = mRectF.height() / 2 + 10;
        mDuihaoOneEndPointX = duihaoOneStartPointX;
        mDuihaoOneEndPointY = duihaoOneStartPointY;

        float duihaoTwoStartPointX = mRectF.left + 6;
        float duihaoTwoStartPointY = mRectF.height() / 2 + 20;
        mDuihaoTwoEndPointX = duihaoTwoStartPointX;
        mDuihaoTwoEndPointY = duihaoTwoStartPointY;

        offset = 0;
        offsetTwo = -270;


        mValueAnimator2 = ValueAnimator.ofFloat(0, -270);
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator2 = new AccelerateDecelerateInterpolator();
        mValueAnimator2.setInterpolator(accelerateDecelerateInterpolator2);
        mValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offsetTwo = (float) valueAnimator.getAnimatedValue();
//                drawArcTwo();
                System.out.println("Offset2:" + offsetTwo);
            }
        });
        mValueAnimator2.setDuration(800);

        mValueAnimator = ValueAnimator.ofFloat(offset, -270);
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        mValueAnimator.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offset = (float) valueAnimator.getAnimatedValue();
                System.out.println("Offset:" + offset);
            }
        });
        mValueAnimator.setDuration(300);

        mValueAnimator.start();
        mValueAnimator2.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValueAnimator3.start();
                mValueAnimator4.start();
            }
        }, 300);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValueAnimator5.start();
                mValueAnimator6.start();
            }
        }, 600);

        float duihaoOneEndPointX = mRectF.left + 6;
        float duihaoOneEndPointY = mRectF.height() / 2 + 20;

        mValueAnimator3 = ValueAnimator.ofFloat(duihaoOneStartPointX, duihaoOneEndPointX);
        mValueAnimator3.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDuihaoOneEndPointX = (float) valueAnimator.getAnimatedValue();
            }
        });
        mValueAnimator3.setDuration(300);

        mValueAnimator4 = ValueAnimator.ofFloat(duihaoOneStartPointY, duihaoOneEndPointY);
        mValueAnimator4.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDuihaoOneEndPointY = (float) valueAnimator.getAnimatedValue();
            }
        });
        mValueAnimator4.setDuration(300);


        float duihaoTwoEndPointX = mRectF.left + 30;
        float duihaoTwoEndPointY = mRectF.height() / 2;

        mValueAnimator5 = ValueAnimator.ofFloat(duihaoTwoStartPointX, duihaoTwoEndPointX);
        mValueAnimator5.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDuihaoTwoEndPointX = (float) valueAnimator.getAnimatedValue();
            }
        });
        mValueAnimator5.setDuration(300);

        mValueAnimator6 = ValueAnimator.ofFloat(duihaoTwoStartPointY, duihaoTwoEndPointY);
        mValueAnimator6.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDuihaoTwoEndPointY = (float) valueAnimator.getAnimatedValue();
            }
        });
        mValueAnimator6.setDuration(300);


//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mValueAnimator2.start();
//            }
//        }, 400);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        View currentView = mViews.get(0);
        View nextView = null;
        nextView = mViews.get(1);
        if (currentView instanceof EditText && nextView instanceof EditText) {
            Rect currentRect = new Rect();
            Rect nextRect = new Rect();
            currentView.getGlobalVisibleRect(currentRect);
            nextView.getGlobalVisibleRect(nextRect);
            int topdiff = 10;
            int width = 60;
            mRectF = new RectF(currentRect.right - (width / 2) - 20, topdiff,
                    currentRect.right + width / 2, currentRect.height() - 16);

            float duihaoOneStartPointX = mRectF.left;
            float duihaoOneStartPointY = mRectF.height() / 2 + 10;
            mDuihaoOneEndPointX = duihaoOneStartPointX;
            mDuihaoOneEndPointY = duihaoOneStartPointY;

            float duihaoTwoStartPointX = mRectF.left + 6;
            float duihaoTwoStartPointY = mRectF.height() / 2 + 20;
            mDuihaoTwoEndPointX = duihaoTwoStartPointX;
            mDuihaoTwoEndPointY = duihaoTwoStartPointY;
        }
        if (mDrawThread == null) {
            mDrawThread = new DrawThread();
        }
//        mDrawThread.start();
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mDrawThread != null) {
            isRun = false;
            mDrawThread = null;
        }
    }

    class DrawThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isRun) {
                Canvas canvas = null;
                try {
                    synchronized (mSurfaceHolder) {
                        canvas = mSurfaceHolder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


//                            canvas.drawRect(rectF, mPaint);
                        canvas.drawArc(mRectF, -270, offset, false, mPaint);
                        canvas.drawArc(mRectF, -270, offsetTwo, false, mPaint2);

                        float duihaoOneStartPointX = mRectF.left;
                        float duihaoOneStartPointY = mRectF.height() / 2 + 10;
                        canvas.drawLine(duihaoOneStartPointX, duihaoOneStartPointY,
                                mDuihaoOneEndPointX, mDuihaoOneEndPointY, mPaint);
                        float duihaoTwoStartPointX = mRectF.left + 6;
                        float duihaoTwoStartPointY = mRectF.height() / 2 + 20;
                        canvas.drawLine(duihaoTwoStartPointX, duihaoTwoStartPointY,
                                mDuihaoTwoEndPointX, mDuihaoTwoEndPointY, mPaint);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            mDrawThread = null;
            System.out.println("线程结束");
        }
    }
}
