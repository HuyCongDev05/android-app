package com.example.test;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.ui.HomeFragment;
import com.example.test.ui.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private View highlight;
    private ImageView btnHome, btnSettings;
    private String currentTab = "HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highlight = findViewById(R.id.highlight);
        btnHome = findViewById(R.id.btn_home);
        btnSettings = findViewById(R.id.btn_settings);

        loadFragment(new HomeFragment());
        btnHome.post(() -> highlight.setX(btnHome.getX()));

        btnHome.setOnClickListener(v -> {
            if (!currentTab.equals("HOME")) {
                currentTab = "HOME";
                moveHighlight(btnHome);
                loadFragment(new HomeFragment());
            }
        });

        btnSettings.setOnClickListener(v -> {
            if (!currentTab.equals("SETTINGS")) {
                currentTab = "SETTINGS";
                moveHighlight(btnSettings);
                loadFragment(new SettingsFragment());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void moveHighlight(View target) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(
                highlight, "x", highlight.getX(), target.getX());
        anim.setDuration(300);
        anim.start();
    }
}
