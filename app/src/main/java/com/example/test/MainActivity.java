package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
//        selectTab(R.id.icon_home, R.id.text_home);
//
//        findViewById(R.id.btn_home).setOnClickListener(v -> {
//            loadFragment(new HomeFragment());
//            selectTab(R.id.icon_home, R.id.text_home);
//        });
//
//        findViewById(R.id.btn_profile).setOnClickListener(v -> {
//            loadFragment(new ProfileFragment());
//            selectTab(R.id.icon_profile, R.id.text_profile);
//        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
