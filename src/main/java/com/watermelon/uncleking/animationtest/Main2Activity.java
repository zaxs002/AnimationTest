package com.watermelon.uncleking.animationtest;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private EditText mEt1;
    private EditText mEt2;
    private AnimationView3 mSurface_view;
    private Paint mPaint;
    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
    }
}
