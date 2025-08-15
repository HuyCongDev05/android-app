package com.example.test.ui.custom;

import android.content.Context;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;

import com.example.test.R;

public class NavigationHelper {

    private final Context context;
    private final ImageButton[] buttons;

    public NavigationHelper(Context context, ImageButton... buttons) {
        this.context = context;
        this.buttons = buttons;
    }

    public void setActiveButton(int activeButtonId) {
        int activeColor = ContextCompat.getColor(context, R.color.nav_active);
        int inactiveColor = ContextCompat.getColor(context, R.color.nav_inactive);

        for (ImageButton btn : buttons) {
            if (btn.getId() == activeButtonId) {
                btn.setColorFilter(activeColor);
            } else {
                btn.setColorFilter(inactiveColor);
            }
        }
    }
}

