package com.yamin.shadowfactory;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yamin.shadowlibrary.Shadow;

public class MainActivity extends AppCompatActivity {

    private float oneDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setShadowDefault();

        oneDP = Resources.getSystem().getDisplayMetrics().density;

        View centerView = findViewById(R.id.main_center_back);
        View footerView = findViewById(R.id.main_footer_back);

        View v1 = findViewById(R.id.main_v1);
        View v2 = findViewById(R.id.main_v2);
        final TextView tv = (TextView) findViewById(R.id.main_tv_default);
        ImageView iv = (ImageView) findViewById(R.id.main_iv);

        LinearLayout llTitleBack = (LinearLayout) findViewById(R.id.main_title_back);
        LinearLayout llScrollContent = (LinearLayout) findViewById(R.id.main_scroll_content);

        setTopAndBottomShadow(centerView);
        setTopShadow(footerView);
        setBottomShadow(llTitleBack);
        setLeftAndRightShadow(v1, v2);
        setAllShadow(iv);
        setDefaultShadow(tv);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(70 * oneDP), (int)(50 * oneDP));
        params.setMargins((int)(5*oneDP), (int)(5*oneDP), (int)(5*oneDP), (int)(5*oneDP));
        Shadow roundShadow = setRoundShadow();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                tv.setText(String.valueOf(Math.pow(2, tag)));
            }
        };
        for (int i = 0; i < 12; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setTag(i);
            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(onClickListener);

            roundShadow.set(textView);
            llScrollContent.addView(textView);
        }
    }

    // setting default setting example
    private void setShadowDefault() {
        Shadow.setDefaultAlpha(55);
        Shadow.setDefaultBlur(6);
        Shadow.setDefaultShadowColorRes(R.color.blue);
    }

    // use the default setting example
    private void setDefaultShadow(View view) {
        Shadow.Builder
                .init(this)
                .shadowAll((int) oneDP)
                .defaultAll()
                .build().set(view);
    }

    // setting only bottom shadow
    // with default parameters example
    private void setBottomShadow(View view) {
        if (view == null) return;

        Shadow shadow = Shadow.Builder
                .init(this)
                .shadowBottom((int) oneDP)
                .defaultParameters()
                .shadowColorRes(R.color.yellow)
                .build();
        shadow.set(view);
    }

    // setting top and bottom shadow example
    private void setTopAndBottomShadow(View view) {
        if (view == null) return;

        Shadow.Builder
                .init(this)
                .shadowTop((int) oneDP)
                .shadowBottom((int) oneDP)
                .defaultAll()
                .build().set(view);
    }

    // setting left and right shadow
    // with custom parameters example
    // use the same shadow in multiple Views
    private void setLeftAndRightShadow(View v1, View v2) {
        if (v1 == null || v2 == null) return;

        Shadow shadow = Shadow.Builder
                .init(this)
                .shadowLeft((int) oneDP)
                .shadowRight((int) oneDP)
                .alpha(60)
                .blur(5)
                .backgroundColorRes(android.R.color.holo_blue_bright)
                .shadowColorRes(R.color.purple)
                .build();

        shadow.set(v1);
        shadow.set(v2);
    }

    // setting only top shadow
    // with custom parameters
    // and default shadow color example
    private void setTopShadow(View view) {
        if (view == null) return;

        Shadow.Builder
                .init(this)
                .shadowTop((int) oneDP)
                .alpha(70)
                .blur(8)
                .backgroundColorRes(R.color.blue)
                .build().set(view);
    }

    // setting all around round shadow
    // with default parameters
    // and default shadow color example
    private Shadow setRoundShadow() {
        return Shadow.Builder
                .init(this)
                .shadowAll((int) oneDP)
                .radius(150)
                .shadowColorRes(R.color.grey)
                .build();
    }

    // setting all around round shadow
    // with custom parameters
    // on a ImageView example
    // should set padding on the ImageView for better results
    private void setAllShadow(ImageView view) {
        if (view == null) return;

        Shadow.Builder
                .init(this)
                .shadowAll((int) oneDP)
                .alpha(60)
                .radius(150)
                .backgroundColorRes(android.R.color.black)
                .shadowColorRes(R.color.grey)
                .build().set(view);
    }
}