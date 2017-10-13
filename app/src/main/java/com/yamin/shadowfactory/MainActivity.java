package com.yamin.shadowfactory;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yamin.shadowlibrary.Shadow;

public class MainActivity extends AppCompatActivity {

    private int oneDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyLog.showLogs(true);
        MyLog.e("here");
        oneDP = (int) Resources.getSystem().getDisplayMetrics().density;

        View centerView = findViewById(R.id.main_center_back);
        View footerView = findViewById(R.id.main_footer_back);

        View v1 = findViewById(R.id.main_v1);
        View v2 = findViewById(R.id.main_v2);
        ImageView iv = (ImageView) findViewById(R.id.main_iv);

        LinearLayout llTitleBack = (LinearLayout) findViewById(R.id.main_title_back);
        LinearLayout llScrollContent = (LinearLayout) findViewById(R.id.main_scroll_content);

        setTopAndBottomShadow(centerView);
        setTopShadow(footerView);
        setBottomShadow(llTitleBack);
        setLeftAndRightShadow(v1, v2);
        setAllShadow(iv);

        LinearLayout.LayoutParams params;
        for (int i = 0; i < 10; i++) {
            View view = new View(this);
            params = new LinearLayout.LayoutParams(70 * oneDP, 50 * oneDP);
            params.setMargins(5*oneDP, 5*oneDP, 5*oneDP, 5*oneDP);
            view.setLayoutParams(params);

            setRoundShadow(view);
            llScrollContent.addView(view);
        }
    }

    private void setBottomShadow(View view) {
        if (view == null) return;

//        MyLogger.d("here");
        Shadow shadow = Shadow.Builder
                .init(this)
                .shadowDown(oneDP)
                .blur(6)
                .alpha(55)
                .shadowColorRes(R.color.yellow)
                .build();
        shadow.set(view);
    }

    private void setTopAndBottomShadow(View view) {
        if (view == null) return;

//        MyLogger.d("here");
        Shadow.Builder
                .init(this)
                .shadowUp(oneDP)
                .shadowDown(oneDP)
                .alpha(30)
                .blur(6)
                .shadowColorRes(R.color.red)
                .build().set(view);
    }

    private void setLeftAndRightShadow(View v1, View v2) {
        if (v1 == null || v2 == null) return;

//        MyLogger.d("here");
        Shadow shadow = Shadow.Builder
                .init(this)
                .shadowLeft(oneDP)
                .shadowRight(oneDP)
                .alpha(60)
                .blur(6)
                .backgroundColorRes(android.R.color.holo_blue_bright)
                .shadowColorRes(R.color.purple)
                .build();

        shadow.set(v1);
        shadow.set(v2);
    }

    private void setTopShadow(View view) {
        if (view == null) return;

//        MyLogger.d("here");
        Shadow.Builder
                .init(this)
                .shadowUp(oneDP)
                .alpha(70)
                .blur(8)
                .backgroundColorRes(R.color.blue)
                .shadowColorRes(R.color.blue)
                .build().set(view);
    }

    private void setRoundShadow(View view) {
        if (view == null) return;

//        Logger.d("here");
        Shadow.Builder
                .init(this)
                .shadowAll(oneDP)
                .radius(150)
                .alpha(55)
                .blur(6)
                .shadowColorRes(R.color.grey)
                .build().set(view);
    }

    private void setAllShadow(ImageView view) {
        if (view == null) return;

        int blurSize = 7;
        int paddingSize = blurSize * oneDP;
        view.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
//        Logger.d("here");
        Shadow.Builder
                .init(this)
                .shadowAll(oneDP)
                .alpha(60)
                .radius(150)
                .blur(blurSize)
                .backgroundColorRes(android.R.color.black)
                .shadowColorRes(R.color.grey)
                .build().set(view);
    }
}