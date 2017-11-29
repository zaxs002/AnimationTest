package com.watermelon.uncleking.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by UncleKing on 2017/11/28.
 */

public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {

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


    public AnimationView(Context context) {
        this(context, null, 0);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    void drawArc() {
        canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        View currentView = mViews.get(0);
        View nextView = null;
        nextView = mViews.get(1);
        if (currentView instanceof EditText && nextView instanceof EditText) {
            Rect currentRect = new Rect();
            Rect nextRect = new Rect();
            currentView.getGlobalVisibleRect(currentRect);
            nextView.getGlobalVisibleRect(nextRect);
            int topdiff = 10;
//                            if (offset < -180) {
//                                topdiff = 10;
//                                rightdiff = 210;

//                            }
            int width = 100;
            RectF rectF = new RectF(currentRect.right - (width / 2) - 20, topdiff, currentRect.right + width / 2, currentRect.height() - 16);
//            canvas.drawRect(rectF, mPaint);
//            float d = -270 + offset;
//            float swa;
//            System.out.println("-----------:" + d);
//            if (d < -430) {
//                swa = 0;
//            } else {
//                swa = offset;
//            }
            canvas.drawArc(rectF, -270, offset, false, mPaint);
//                    canvas.drawArc(rectF, -270 + count, offset + count, false, mPaint2);
            canvas.drawArc(rectF, -270, offsetTwo, false, mPaint2);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    void drawArcTwo() {
        canvas = mSurfaceHolder.lockCanvas();
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        View currentView = mViews.get(0);
        View nextView = null;
        nextView = mViews.get(1);
        if (currentView instanceof EditText && nextView instanceof EditText) {
            Rect currentRect = new Rect();
            Rect nextRect = new Rect();
            currentView.getGlobalVisibleRect(currentRect);
            nextView.getGlobalVisibleRect(nextRect);
            int topdiff = 10;
            int width = 100;
            RectF rectF = new RectF(currentRect.right - (width / 2) - 20, topdiff, currentRect.right + width / 2, currentRect.height() - 16);
//                            canvas.drawRect(rectF, mPaint);
            canvas.drawArc(rectF, -270, offsetTwo, false, mPaint2);
//                    canvas.drawArc(rectF, -270 + count, offset + count, false, mPaint2);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void addElement(View view) {
        mViews.add(view);
    }

    public void start() {
//        if (mDrawThread == null) {
//            mDrawThread = new DrawThread();
//        }
//        offset = 0;
//        mDrawThread.interrupt();
//        mDrawThread.start();
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
        mValueAnimator2.setDuration(400);

        mValueAnimator = ValueAnimator.ofFloat(offset, -270);
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        mValueAnimator.setInterpolator(accelerateDecelerateInterpolator);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offset = (float) valueAnimator.getAnimatedValue();
                drawArc();
//                if (offset < -150f && !valueAnimator2.isStarted()) {
//                    valueAnimator2.start();
//                }
                System.out.println("Offset:" + offset);
            }
        });
        mValueAnimator.setDuration(800);

        mValueAnimator.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValueAnimator2.start();
            }
        },400);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mDrawThread == null) {
            mDrawThread = new DrawThread();
        }
//        mDrawThread.start();
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
            int offsetDiff = 0;
            int count = 0;
            while (offset > -320) {
                Canvas canvas = null;
                try {
                    synchronized (mSurfaceHolder) {
                        canvas = mSurfaceHolder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        View currentView = mViews.get(0);
                        View nextView = null;
                        nextView = mViews.get(1);
                        if (currentView instanceof EditText && nextView instanceof EditText) {
                            Rect currentRect = new Rect();
                            Rect nextRect = new Rect();
                            currentView.getGlobalVisibleRect(currentRect);
                            nextView.getGlobalVisibleRect(nextRect);
                            int topdiff = 10;
                            int rightdiff = 200;
//                            if (offset < -180) {
//                                topdiff = 10;
//                                rightdiff = 210;
//                            }
                            RectF rectF = new RectF(currentRect.right - 60, topdiff, currentRect.width() + rightdiff, currentRect.height() - 16);
//                            canvas.drawRect(rectF, mPaint);
                            canvas.drawArc(rectF, -270, offset * 4, false, mPaint);
                            canvas.drawArc(rectF, -270 + count, offset + count, false, mPaint2);
                            offset -= 10;

                            offsetDiff -= 20;
                            count += 10;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    mDrawThread = null;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程结束");
        }
    }
}
