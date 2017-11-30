package com.watermelon.uncleking.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by UncleKing on 2017/11/29.
 */

public class AnimationView3 extends SurfaceView implements SurfaceHolder.Callback {
    private ArrayList<View> mViews = new ArrayList<>();
    private SurfaceHolder mSurfaceHolder;
    private DrawThread mDrawThread;

    private Paint mPaint;
    private Paint mPaint2;

    boolean isRun = true;
    private RectF mRectF;
    private ValueAnimator mArcAnimator;
    private float mArcValue;
    private PointF mLineOnePointStart;
    private PointF mLineOnePointEnd;
    private float mLineValueY;
    private float mLineValueX2;
    private ValueAnimator mLineAnimator;
    private Handler mHandler = new Handler();
    private PointF mLineTwoPointEnd;
    private PointF mLineTwoPointStart;
    private float mLineValueX4;
    private ValueAnimator mLineAnimator2;
    private float mLineClearValueX2;
    private ValueAnimator mLineClearAnimator1;
    private float mArcClearValue;
    private ValueAnimator mArcClearAnimator;
    private int mDuration;

    public AnimationView3(Context context) {
        this(context, null, 0);
    }

    public AnimationView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.parseColor("#34afe5"));

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(5);
        mPaint2.setColor(Color.parseColor("#FAFAFA"));
    }

    public void start() {
        mArcValue = 0;
        mLineValueX2 = 0;
        mLineValueX4 = mLineTwoPointStart.x;
        mLineClearValueX2 = mLineOnePointStart.x;
        mArcClearValue = 0;
        mDuration = 300;


        //第一条线动画
        mLineAnimator = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
        mLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineValueX2 = (float) animation.getAnimatedValue();
            }
        });
        mLineAnimator.setDuration(mDuration);


        //圆动画
        mArcAnimator = ValueAnimator.ofFloat(0, 180);
        mArcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcValue = (float) animation.getAnimatedValue();
            }
        });

        mArcAnimator.setDuration(mDuration);


        //第二条线动画
        mLineAnimator2 = ValueAnimator.ofFloat(mLineTwoPointStart.x, mLineTwoPointEnd.x);
        mLineAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineValueX4 = (float) animation.getAnimatedValue();
            }
        });
        mLineAnimator2.setDuration(mDuration);


        //第一条线消除动画
        mLineClearAnimator1 = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
        mLineClearAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLineClearValueX2 = (float) animation.getAnimatedValue();
            }
        });
        mLineClearAnimator1.setDuration(mDuration);

        //圆消除动画
        mArcClearAnimator = ValueAnimator.ofFloat(0, 180);
        mArcClearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcClearValue = (float) animation.getAnimatedValue();
            }
        });
        mArcClearAnimator.setDuration(mDuration);


        mLineAnimator.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mArcAnimator.start();
            }
        }, mDuration - (mDuration / 8));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLineClearAnimator1.start();
            }
        }, mDuration + (mDuration / 4));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mArcClearAnimator.start();
            }
        }, mDuration * 2 + (mDuration / 8));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLineAnimator2.start();
            }
        }, mDuration * 2 - (mDuration / 4));

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        View currentView = mViews.get(0);
        View nextView = null;
        nextView = mViews.get(1);
        if (currentView instanceof EditText && nextView instanceof EditText) {
            Rect outRect = new Rect();
            getWindowVisibleDisplayFrame(outRect);
            int actionBarHeight = outRect.height() - getHeight();

            int statusBarHeight = getStatusBarHeight2(this.getContext());

            int actionStatusBarHeight = actionBarHeight + statusBarHeight;

            Rect currentRect = new Rect();
            Rect nextRect = new Rect();
            currentView.getGlobalVisibleRect(currentRect);
            nextView.getGlobalVisibleRect(nextRect);

            int width = 100;
            mLineOnePointStart = new PointF(currentRect.left + 8, currentRect.bottom - 16 - actionStatusBarHeight);
            mLineOnePointEnd = new PointF(currentRect.right - 8, currentRect.bottom - 16 - actionStatusBarHeight);

            mLineTwoPointStart = new PointF(nextRect.right - 8, nextRect.bottom - 16 - actionStatusBarHeight);
            mLineTwoPointEnd = new PointF(nextRect.left + 8, nextRect.bottom - 16 - actionStatusBarHeight);
            mRectF = new RectF(currentRect.right - (width / 2) - 20, currentRect.bottom - 16 - actionStatusBarHeight,
                    currentRect.right + width / 2, nextRect.bottom - 16 - actionStatusBarHeight);

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

    }

    public void addElement(View view) {
        mViews.add(view);
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

                        float x = (getWidth() - getHeight() / 2) / 2;
                        float y = getHeight() / 4;

                        RectF oval = new RectF(x, y, getWidth() - x, getHeight() - y);

                        canvas.drawRect(oval, mPaint);
                        canvas.drawArc(oval, 270, 180, false, mPaint);

//                        canvas.drawRect(mRectF, mPaint);
//                        canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineValueX2, mLineOnePointEnd.y, mPaint);
                        canvas.drawLine(mLineClearValueX2, mLineOnePointStart.y, mLineValueX2, mLineOnePointEnd.y, mPaint);
                        //第一条消除线
//                        canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineClearValueX2, mLineOnePointEnd.y, mPaint2);
                        canvas.drawArc(mRectF, 270, mArcValue, false, mPaint);
                        //消除动画
                        canvas.drawArc(mRectF, 270, mArcClearValue, false, mPaint2);

                        canvas.drawLine(mLineTwoPointStart.x, mLineTwoPointStart.y, mLineValueX4, mLineTwoPointEnd.y, mPaint);

//                        canvas.drawArc(mRectF, -270, offset, false, mPaint);
//                        canvas.drawArc(mRectF, -270, offsetTwo, false, mPaint2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            mDrawThread = null;
            System.out.println("线程结束");
        }
    }


    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.d(TAG, "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }
    }

    public static int getStatusBarHeight2(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
