package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.ui.HomeFragment;
import com.example.test.ui.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
        selectTab(R.id.icon_home, R.id.text_home);

        findViewById(R.id.btn_home).setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            selectTab(R.id.icon_home, R.id.text_home);
        });

        findViewById(R.id.btn_follow).setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            selectTab(R.id.icon_follow, R.id.text_follow);
        });

        findViewById(R.id.btn_category).setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            selectTab(R.id.icon_category, R.id.text_category);
        });

        findViewById(R.id.btn_notify).setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            selectTab(R.id.icon_notify, R.id.text_notify);
        });

        findViewById(R.id.btn_profile).setOnClickListener(v -> {
            loadFragment(new ProfileFragment());
            selectTab(R.id.icon_profile, R.id.text_profile);
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void selectTab(int selectedIconId, int selectedTextId) {
        int[] iconIds = {R.id.icon_home, R.id.icon_follow, R.id.icon_category, R.id.icon_notify, R.id.icon_profile};
        int[] textIds = {R.id.text_home, R.id.text_follow, R.id.text_category, R.id.text_notify, R.id.text_profile};

        for (int i = 0; i < iconIds.length; i++) {
            ImageView icon = findViewById(iconIds[i]);
            TextView text = findViewById(textIds[i]);

            if (iconIds[i] == selectedIconId && textIds[i] == selectedTextId) {
                // Tab đang chọn → hồng
                text.setTextColor(Color.parseColor("#FF4D8C"));

                // đổi icon sang ảnh màu hồng / click
                if (iconIds[i] == R.id.icon_home) {
                    icon.setImageResource(R.drawable.icon_home_click);
                } else if (iconIds[i] == R.id.icon_follow) {
                    icon.setImageResource(R.drawable.icon_follow_click);
                } else if (iconIds[i] == R.id.icon_category) {
                    icon.setImageResource(R.drawable.icon_category_click);
                } else if (iconIds[i] == R.id.icon_notify) {
                    icon.setImageResource(R.drawable.icon_notify_click);
                } else if (iconIds[i] == R.id.icon_profile) {
                    icon.setImageResource(R.drawable.icon_profile_click);
                }
            } else {
                // Các tab khác → màu trắng
                text.setTextColor(Color.WHITE);

                // đổi icon về mặc định
                if (iconIds[i] == R.id.icon_home) {
                    icon.setImageResource(R.drawable.icon_home);
                } else if (iconIds[i] == R.id.icon_follow) {
                    icon.setImageResource(R.drawable.icon_follow);
                } else if (iconIds[i] == R.id.icon_category) {
                    icon.setImageResource(R.drawable.icon_category);
                } else if (iconIds[i] == R.id.icon_notify) {
                    icon.setImageResource(R.drawable.icon_notify);
                } else if (iconIds[i] == R.id.icon_profile) {
                    icon.setImageResource(R.drawable.icon_profile);
                }
            }
        }
    }

}
