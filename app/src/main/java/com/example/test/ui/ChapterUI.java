package com.example.test.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class ChapterUI extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        TextView chapterName = findViewById(R.id.chapterName);
        ImageView btnBack = findViewById(R.id.btnBack);
        chapterName.setText(ComicDetailFragment.chapterName);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }
}
