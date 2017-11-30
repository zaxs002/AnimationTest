package com.watermelon.uncleking.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

/**
 * Created by UncleKing on 2017/11/30.
 */

public class AnimationEditText extends android.support.v7.widget.AppCompatEditText {

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

    public static final int STATUS_CLEAR_ALL = 0;
    public static final int STATUS_CLEAR_DUIHAO = -1;
    public static final int STATUS_CLEAR_LINE = -2;
    public static final int STATUS_ONELINE = 1;
    public static final int STATUS_DUIHAO = 2;
    public static final int STATUS_DUIHAO_AND_LINEONE = 3;
    public static final int STATUS_QIEHUAN = 4;
    public static final int STATUS_TWOLINE = 5;

    public int mStatus = STATUS_CLEAR_ALL;
    private RectF mRectFArc;
    private float mDuihaoOneEndPointX;
    private float mDuihaoOneEndPointY;
    private float mDuihaoTwoEndPointX;
    private float mDuihaoTwoEndPointY;
    private float offset;
    private float offsetTwo;
    private ValueAnimator mValueAnimator2;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mValueAnimator3;
    private ValueAnimator mValueAnimator4;
    private ValueAnimator mValueAnimator5;
    private ValueAnimator mValueAnimator6;

    public AnimationEditText(Context context) {
        super(context);
        init();
    }

    public AnimationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setBackground(null);

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


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect currentRect = new Rect();
                Rect nextRect = new Rect();
                currentRect.top = 0;
                currentRect.bottom = getHeight();
                currentRect.left = 0;
                currentRect.right = getWidth();
//                getGlobalVisibleRect(currentRect);
                //TODO
//        nextView.getGlobalVisibleRect(nextRect);

                int width = 60;
                mLineOnePointStart = new PointF(currentRect.left + 8, currentRect.bottom - 16);
                mLineOnePointEnd = new PointF(currentRect.right - width / 10 - width / 2, currentRect.bottom - 16);

                mLineTwoPointStart = new PointF(nextRect.right - 8, nextRect.bottom - 16);
                mLineTwoPointEnd = new PointF(nextRect.left + 8, nextRect.bottom - 16);
                mRectF = new RectF(currentRect.right - (width / 2) - 20, currentRect.bottom - 16,
                        currentRect.right + width / 2, nextRect.bottom - 16);

                mArcValue = 0;
                mLineValueX2 = mLineOnePointStart.x;
                mLineValueX4 = mLineTwoPointStart.x;
                mLineClearValueX2 = mLineOnePointStart.x;
                mArcClearValue = 0;
                mDuration = 300;

                //圆Rect
                int arcWidth = 60;
                mRectFArc = new RectF(currentRect.right - arcWidth - width / 10 - 20, currentRect.top + 10,
                        currentRect.right - arcWidth / 10, currentRect.bottom - 16);


                float duihaoOneStartPointX = mRectFArc.left;
                float duihaoOneStartPointY = mRectFArc.height() / 2 + 10;
                mDuihaoOneEndPointX = duihaoOneStartPointX;
                mDuihaoOneEndPointY = duihaoOneStartPointY;

                float duihaoTwoStartPointX = mRectFArc.left + 6;
                float duihaoTwoStartPointY = mRectFArc.height() / 2 + 20;
                mDuihaoTwoEndPointX = duihaoTwoStartPointX;
                mDuihaoTwoEndPointY = duihaoTwoStartPointY;
            }
        });
    }

    public void drawDuiHao() {
        mStatus = STATUS_DUIHAO;
        animation();
    }

    public void animation() {
        switch (mStatus) {
            case STATUS_CLEAR_ALL:
                mArcValue = 0;
                mLineValueX2 = mLineOnePointStart.x;
                mLineValueX4 = mLineTwoPointStart.x;
                mLineClearValueX2 = mLineOnePointEnd.x;
                mArcClearValue = 0;
                mDuration = 300;
                offset = 0;
                offsetTwo = -270;
                mDuihaoOneEndPointX = mRectFArc.left;
                mDuihaoOneEndPointY = mRectFArc.height() / 2 + 10;
                mDuihaoTwoEndPointX = mRectFArc.left + 6;
                mDuihaoTwoEndPointY = mRectFArc.height() / 2 + 20;
                break;
            case STATUS_CLEAR_DUIHAO:
                offset = 0;
                offsetTwo = -270;
                mDuihaoOneEndPointX = mRectFArc.left;
                mDuihaoOneEndPointY = mRectFArc.height() / 2 + 10;
                mDuihaoTwoEndPointX = mRectFArc.left + 6;
                mDuihaoTwoEndPointY = mRectFArc.height() / 2 + 20;
                break;
            case STATUS_CLEAR_LINE:
//                canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineClearValueX2, mLineOnePointEnd.y, mPaint2);
                mDuration = 800;

                //第一条线消除动画
                mLineClearAnimator1 = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
                mLineClearAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mLineClearValueX2 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                mLineClearAnimator1.setDuration(mDuration);
                mLineClearAnimator1.start();
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLineClearAnimator1.start();
//                    }
//                }, mDuration - mDuration / 4);
                break;
            case STATUS_ONELINE:
                mLineClearValueX2 = mLineOnePointStart.x;
                mDuration = 800;
//第一条线动画
                mLineAnimator = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
                mLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mLineValueX2 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                mLineAnimator.setDuration(mDuration);
                mLineAnimator.start();
                break;
            case STATUS_DUIHAO:
                //第一条线动画
//                mLineAnimator = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
//                mLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mLineValueX2 = (float) animation.getAnimatedValue();
//                    }
//                });
//                mLineAnimator.setDuration(mDuration);
//                mLineAnimator.start();

                mDuration = 800;

                float duihaoOneStartPointX = mRectFArc.left;
                float duihaoOneStartPointY = mRectFArc.height() / 2 + 10;
                mDuihaoOneEndPointX = duihaoOneStartPointX;
                mDuihaoOneEndPointY = duihaoOneStartPointY;

                float duihaoTwoStartPointX = mRectFArc.left + 6;
                float duihaoTwoStartPointY = mRectFArc.height() / 2 + 20;
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
                        invalidate();
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
                        invalidate();
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

                float duihaoOneEndPointX = mRectFArc.left + 6;
                float duihaoOneEndPointY = mRectFArc.height() / 2 + 20;

                mValueAnimator3 = ValueAnimator.ofFloat(duihaoOneStartPointX, duihaoOneEndPointX);
                mValueAnimator3.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoOneEndPointX = (float) valueAnimator.getAnimatedValue();
                        invalidate();
                    }
                });
                mValueAnimator3.setDuration(300);

                mValueAnimator4 = ValueAnimator.ofFloat(duihaoOneStartPointY, duihaoOneEndPointY);
                mValueAnimator4.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoOneEndPointY = (float) valueAnimator.getAnimatedValue();
                        invalidate();
                    }
                });
                mValueAnimator4.setDuration(300);


                float duihaoTwoEndPointX = mRectFArc.left + 30;
                float duihaoTwoEndPointY = mRectFArc.height() / 2;

                mValueAnimator5 = ValueAnimator.ofFloat(duihaoTwoStartPointX, duihaoTwoEndPointX);
                mValueAnimator5.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoTwoEndPointX = (float) valueAnimator.getAnimatedValue();
                        invalidate();
                    }
                });
                mValueAnimator5.setDuration(300);

                mValueAnimator6 = ValueAnimator.ofFloat(duihaoTwoStartPointY, duihaoTwoEndPointY);
                mValueAnimator6.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoTwoEndPointY = (float) valueAnimator.getAnimatedValue();
                        invalidate();
                    }
                });
                mValueAnimator6.setDuration(300);
                break;
            case STATUS_DUIHAO_AND_LINEONE:

                mDuration = 300;
                //第一条线直接花完
                mLineValueX2 = mLineOnePointEnd.x;
//                mLineAnimator = ValueAnimator.ofFloat(mLineOnePointStart.x, mLineOnePointEnd.x);
//                mLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mLineValueX2 = (float) animation.getAnimatedValue();
//                    }
//                });
//                mLineAnimator.setDuration(mDuration);
//                mLineAnimator.start();


                duihaoOneStartPointX = mRectFArc.left;
                duihaoOneStartPointY = mRectFArc.height() / 2 + 10;
                mDuihaoOneEndPointX = duihaoOneStartPointX;
                mDuihaoOneEndPointY = duihaoOneStartPointY;

                duihaoTwoStartPointX = mRectFArc.left + 6;
                duihaoTwoStartPointY = mRectFArc.height() / 2 + 20;
                mDuihaoTwoEndPointX = duihaoTwoStartPointX;
                mDuihaoTwoEndPointY = duihaoTwoStartPointY;

                offset = 0;
                offsetTwo = -270;


                mValueAnimator2 = ValueAnimator.ofFloat(0, -270);
                accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
                mValueAnimator2.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        offsetTwo = (float) valueAnimator.getAnimatedValue();
//                drawArcTwo();
                        System.out.println("Offset2:" + offsetTwo);
                    }
                });
                mValueAnimator2.setDuration(mDuration * 2 + mDuration / 3 * 2);//800

                mValueAnimator = ValueAnimator.ofFloat(offset, -270);
                mValueAnimator.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        offset = (float) valueAnimator.getAnimatedValue();
                        System.out.println("Offset:" + offset);
                    }
                });
                mValueAnimator.setDuration(mDuration);

//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                mValueAnimator.start();
                mValueAnimator2.start();
//                    }
//                }, mDuration - mDuration / 2);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValueAnimator3.start();
                        mValueAnimator4.start();
                    }
                }, mDuration);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValueAnimator5.start();
                        mValueAnimator6.start();
                    }
                }, mDuration * 2);

                duihaoOneEndPointX = mRectFArc.left + 6;
                duihaoOneEndPointY = mRectFArc.height() / 2 + 20;

                mValueAnimator3 = ValueAnimator.ofFloat(duihaoOneStartPointX, duihaoOneEndPointX);
                mValueAnimator3.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoOneEndPointX = (float) valueAnimator.getAnimatedValue();
                    }
                });
                mValueAnimator3.setDuration(mDuration);

                mValueAnimator4 = ValueAnimator.ofFloat(duihaoOneStartPointY, duihaoOneEndPointY);
                mValueAnimator4.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoOneEndPointY = (float) valueAnimator.getAnimatedValue();
                    }
                });
                mValueAnimator4.setDuration(mDuration);


                duihaoTwoEndPointX = mRectFArc.left + 30;
                duihaoTwoEndPointY = mRectFArc.height() / 2;

                mValueAnimator5 = ValueAnimator.ofFloat(duihaoTwoStartPointX, duihaoTwoEndPointX);
                mValueAnimator5.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoTwoEndPointX = (float) valueAnimator.getAnimatedValue();
                    }
                });
                mValueAnimator5.setDuration(mDuration);

                mValueAnimator6 = ValueAnimator.ofFloat(duihaoTwoStartPointY, duihaoTwoEndPointY);
                mValueAnimator6.setInterpolator(accelerateDecelerateInterpolator);
                mValueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mDuihaoTwoEndPointY = (float) valueAnimator.getAnimatedValue();
                    }
                });
                mValueAnimator6.setDuration(mDuration);
                break;
            case STATUS_QIEHUAN:
                mDuration = 400;
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


//                mLineAnimator.start();
//
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                mArcAnimator.start();
//                    }
//                }, mDuration - (mDuration / 8));

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
                }, mDuration + mDuration / 2);
                break;
            case STATUS_TWOLINE:

                break;
        }
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (focused) {
            mStatus = STATUS_ONELINE;
            animation();
        } else {
            mStatus = STATUS_CLEAR_LINE;
            animation();
        }
        System.out.println("Direction:" + direction);
        System.out.println("previouslyFocusedRect:" + previouslyFocusedRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        float x = getX();
//        float y = getY();
//        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
//        canvas.drawRect(rectF, mPaint);

//        canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineValueX2, mLineOnePointEnd.y, mPaint);


        //对号圆
        canvas.drawArc(mRectFArc, -270, offset, false, mPaint);
        canvas.drawArc(mRectFArc, -270, offsetTwo, false, mPaint2);

        //对号1
        float duihaoOneStartPointX = mRectFArc.left;
        float duihaoOneStartPointY = mRectFArc.height() / 2 + 10;
        canvas.drawLine(duihaoOneStartPointX, duihaoOneStartPointY,
                mDuihaoOneEndPointX, mDuihaoOneEndPointY, mPaint);

        //对号2
        float duihaoTwoStartPointX = mRectFArc.left + 6;
        float duihaoTwoStartPointY = mRectFArc.height() / 2 + 20;
        canvas.drawLine(duihaoTwoStartPointX, duihaoTwoStartPointY,
                mDuihaoTwoEndPointX, mDuihaoTwoEndPointY, mPaint);


//                        canvas.drawRect(mRectF, mPaint);
//                        canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineValueX2, mLineOnePointEnd.y, mPaint);


        canvas.drawLine(mLineClearValueX2, mLineOnePointStart.y, mLineValueX2, mLineOnePointEnd.y, mPaint);

        //第一条消除线
//        canvas.drawLine(mLineOnePointStart.x, mLineOnePointStart.y, mLineClearValueX2, mLineOnePointEnd.y, mPaint2);

        canvas.drawArc(mRectF, 270, mArcValue, false, mPaint);
        //消除动画
        canvas.drawArc(mRectF, 270, mArcClearValue, false, mPaint2);

        canvas.drawLine(mLineTwoPointStart.x, mLineTwoPointStart.y, mLineValueX4, mLineTwoPointEnd.y, mPaint);
    }

    public static int getStatusBarHeight2(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
