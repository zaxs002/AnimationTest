package com.watermelon.uncleking.animationtest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private EditText mEt1;
    private EditText mEt2;
    private AnimationView2 mSurface_view;
    private Paint mPaint;
    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt1 = findViewById(R.id.et1);
        mEt2 = findViewById(R.id.et2);
        mBt = findViewById(R.id.start);
        mSurface_view = findViewById(R.id.surface_view);

        mSurface_view.addElement(mEt1);
        mSurface_view.addElement(mEt2);

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurface_view.start();
            }
        });
//        mSurface_view.post(new Runnable() {
//            @Override
//            public void run() {
//                mSurface_view.start();
//            }
//        });
    }
}
