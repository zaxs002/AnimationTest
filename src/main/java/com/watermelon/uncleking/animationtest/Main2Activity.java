package com.watermelon.uncleking.animationtest;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private EditText mEt1;
    private EditText mEt2;
    private AnimationEditText mEt3;
    private AnimationView4 mSurface_view;
    private Paint mPaint;
    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mEt1 = findViewById(R.id.et1);
        mEt2 = findViewById(R.id.et2);
        mEt3 = findViewById(R.id.animationEditText);
        mBt = findViewById(R.id.start);


        mSurface_view = findViewById(R.id.surface_view);


        mSurface_view.addElement(mEt1);
        mSurface_view.addElement(mEt2);

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEt3.drawDuiHao();
            }
        });

        mEt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mEt1.getText().toString().equals("Hello23")) {
                        mSurface_view.mStatus = AnimationView4.STATUS_DUIHAO_AND_LINEONE;
                        mSurface_view.start();
                    } else {
                        mSurface_view.mStatus = AnimationView4.STATUS_ONELINE;
                        mSurface_view.start();
                    }
                }
            }
        });

        mEt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSurface_view.mStatus = AnimationView4.STATUS_QIEHUAN;
                    mSurface_view.start();
                } else {
                    mSurface_view.mStatus = AnimationView4.STATUS_CLEAR_ALL;
                    mSurface_view.start();
                }
            }
        });

        mEt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
                if (s.toString().equals("Hello23")) {
                    System.out.println("对号动画");
                    mSurface_view.mStatus = AnimationView4.STATUS_DUIHAO;
                    mSurface_view.start();
                } else {
                    mSurface_view.mStatus = AnimationView4.STATUS_CLEAR_DUIHAO;
                    mSurface_view.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
