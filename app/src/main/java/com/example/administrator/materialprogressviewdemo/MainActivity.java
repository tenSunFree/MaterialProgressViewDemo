package com.example.administrator.materialprogressviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

public class MainActivity extends AppCompatActivity {

    private ZzHorizontalProgressBar expZzHorizontalProgressBar;
    private TextView nameTextView, feedingTextView, expTextView, levelTextView;
    private LinearLayout feedingLinearLayout;
    private int addedValue, expectedIncreaseInValue, currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** 對文字進行自動縮放 */
        nameTextView = findViewById(R.id.nameTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(nameTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(nameTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        feedingTextView = findViewById(R.id.feedingTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(feedingTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(feedingTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        expTextView = findViewById(R.id.expTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(expTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(expTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        levelTextView = findViewById(R.id.levelTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(levelTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(levelTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);

        /** 每次點擊餵食按鈕, 會產生一個隨機的數值, 並累加到原有的Progress */
        currentLevel = 1;
        expZzHorizontalProgressBar = findViewById(R.id.expZzHorizontalProgressBar);
        feedingLinearLayout = findViewById(R.id.feedingLinearLayout);
        feedingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedingLinearLayout.setClickable(false);
                addedValue = 0;
                expectedIncreaseInValue = (int) (Math.random() * 31 + 50);                          // 50-80中隨機取一個數
                Toast.makeText(MainActivity.this, "+" + expectedIncreaseInValue, Toast.LENGTH_SHORT).show();
                experienceUpdate();
            }
        });
    }

    /** 根據得到的數值 將它累加到原來的Progress, 如果達到100 會自動升級 */
    private void experienceUpdate() {
        expZzHorizontalProgressBar.setProgress(expZzHorizontalProgressBar.getProgress() + 1);
        Log.v("more", "expZzHorizontalProgressBar.getProgress(): "
                + expZzHorizontalProgressBar.getProgress());
        expTextView.setText((expZzHorizontalProgressBar.getProgress() + 1) + "%");
        addedValue++;
        if (expZzHorizontalProgressBar.getProgress() == 100) {
            currentLevel++;
            levelTextView.setText("Lv." + currentLevel);
            expZzHorizontalProgressBar.setProgress(0);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (addedValue < expectedIncreaseInValue) {
                    experienceUpdate();
                } else {
                    feedingLinearLayout.setClickable(true);
                }
            }
        }, 20);
    }
}
