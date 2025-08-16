package com.example.test.animation;

import android.view.View;
import android.widget.TextView;

public class AnimationMoveUnderline {

    private View underline;
    private TextView tabDetail, tabChapter;
    private boolean isDetailSelected = true;

    public AnimationMoveUnderline(View underline, TextView tabDetail, TextView tabChapter) {
        this.underline = underline;
        this.tabDetail = tabDetail;
        this.tabChapter = tabChapter;

        underline.post(new Runnable() {
            @Override
            public void run() {
                moveUnderline(true);
            }
        });

        setupClickListeners();
    }

    private void setupClickListeners() {
        tabDetail.setOnClickListener(v -> {
            if (!isDetailSelected) {
                moveUnderline(true);
                isDetailSelected = true;
                tabDetail.setTextColor(0xFFFFFFFF); // trắng
                tabChapter.setTextColor(0xFF555555); // xám
                // TODO: cập nhật nội dung CardView
            }
        });

        tabChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDetailSelected) {
                    moveUnderline(false);
                    isDetailSelected = false;
                    tabChapter.setTextColor(0xFFFFFFFF);
                    tabDetail.setTextColor(0xFF555555);
                    // TODO: cập nhật nội dung CardView
                }
            }
        });
    }

    public void moveUnderline(final boolean toDetail) {
        final TextView targetTab = toDetail ? tabDetail : tabChapter;

        targetTab.post(() -> {
            int tabLeft = targetTab.getLeft();
            int tabWidth = targetTab.getWidth();
            int underlineWidth = underline.getWidth();

            float targetX = tabLeft + (tabWidth - underlineWidth) / 2f;

            underline.animate()
                    .translationX(targetX)
                    .setDuration(200)
                    .start();
        });
    }
}
