package com.example.test.animation;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class AnimationUnderline {

    private final View underline;
    private final TextView tabDetail;
    private final TextView tabChapter;
    private int duration = 250;
    private float shrinkRatio = 0.6f;

    public AnimationUnderline(View underline, TextView tabDetail, TextView tabChapter) {
        this.underline = underline;
        this.tabDetail = tabDetail;
        this.tabChapter = tabChapter;

        underline.post(() -> setUnderline(tabDetail, false));

        tabDetail.setOnClickListener(v -> setUnderline(tabDetail, true));
        tabChapter.setOnClickListener(v -> setUnderline(tabChapter, true));
    }

    private void setUnderline(TextView tab, boolean animate) {
        int newWidth = (int) (tab.getWidth() * shrinkRatio);
        underline.getLayoutParams().width = newWidth;
        underline.requestLayout();

        float targetX = tab.getLeft() + (tab.getWidth() - newWidth) / 2f;

        if (animate) {
            underline.animate()
                    .translationX(targetX)
                    .setDuration(duration)
                    .start();
        } else {
            underline.setTranslationX(targetX);
        }
        tabDetail.setTextColor(tab == tabDetail ? Color.WHITE : Color.parseColor("#555555"));
        tabChapter.setTextColor(tab == tabChapter ? Color.WHITE : Color.parseColor("#555555"));
    }
}
