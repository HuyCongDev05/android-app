package com.example.test.ui.custom;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class AutoStartImageView extends AppCompatImageView {
    public AutoStartImageView(Context c, AttributeSet a) { super(c, a); }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable d = getDrawable();
        if (d instanceof Animatable) ((Animatable) d).start();
    }

    @Override
    protected void onDetachedFromWindow() {
        Drawable d = getDrawable();
        if (d instanceof Animatable) ((Animatable) d).stop();
        super.onDetachedFromWindow();
    }
}
