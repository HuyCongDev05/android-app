package com.example.test.animation;

import android.app.Activity;

import com.example.test.R;

public class animationBack {
    public static void apply(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
