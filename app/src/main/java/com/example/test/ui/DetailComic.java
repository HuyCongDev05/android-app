package com.example.test.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.animation.AnimationMoveUnderline;

public class DetailComic extends AppCompatActivity {
    private TextView tabDetail, tabChapter;
    private View underline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic); // layout XML của bạn

        tabDetail = findViewById(R.id.tabDetail);
        tabChapter = findViewById(R.id.tabChapter);
        underline = findViewById(R.id.underline);

        // Khởi tạo animation helper
        AnimationMoveUnderline animator = new AnimationMoveUnderline(
                underline,
                tabDetail,
                tabChapter
        );
    }
}
