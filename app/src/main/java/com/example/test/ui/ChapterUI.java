package com.example.test.ui;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.test.R;
import com.example.test.service.ChapterComicService;
import com.example.test.util.ChapterUtils;

import java.util.List;

public class ChapterUI extends AppCompatActivity {

    private String chapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        TextView chapterName = findViewById(R.id.chapterName);
        ImageView btnBack = findViewById(R.id.btnBack);
        chapterName.setText(ComicDetailFragment.chapterName);
        chapter = ChapterUtils.extractChapterNumber(ComicDetailFragment.chapterName);
        ImageView btnNextChapter = findViewById(R.id.btnNextChapter);
        ImageView btnBackChapter = findViewById(R.id.btnBackChapter);
        String lastChapter = ComicDetailFragment.arr[ComicDetailFragment.arr.length - 1];
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        ChapterComicService service = new ChapterComicService();
        service.handleImagesChapterComic(chapter, this::loadImages);

        if (chapter.equals(ComicDetailFragment.arr[0])) {
            btnBackChapter.setImageResource(R.drawable.icon_back_chapter_max);
        }

        btnBackChapter.setOnClickListener(v -> {
            int index = Integer.parseInt(chapter) - 1;
            service.handleImagesChapterComic(String.valueOf(index), this::loadImages);
            btnNextChapter.setImageResource(R.drawable.icon_next_chapter);
            chapterName.setText("Chapter " + index);
            chapter = String.valueOf(index);
        });

        if (!chapter.equals(lastChapter)) {
            btnNextChapter.setOnClickListener(v -> {
                int index = Integer.parseInt(chapter) + 1;
                service.handleImagesChapterComic(String.valueOf(index), this::loadImages);
                btnBackChapter.setImageResource(R.drawable.icon_back_chapter);
                chapterName.setText("Chapter " + index);
                chapter = String.valueOf(index);
            });
        } else {
            btnNextChapter.setImageResource(R.drawable.icon_next_chapter_max);
        }
    }

    public void loadImages(List<String> imageUrls) {
        RecyclerView recyclerView = findViewById(R.id.recyclerImages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new RecyclerView.Adapter<>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ImageView imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                imageView.setAdjustViewBounds(true); // giữ tỉ lệ
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return new RecyclerView.ViewHolder(imageView) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ImageView imageView = (ImageView) holder.itemView;
                Glide.with(imageView.getContext())
                        .load(imageUrls.get(position))
                        .override(Target.SIZE_ORIGINAL) // load ảnh gốc, không resize
                        .placeholder(R.drawable.default_comic)
                        .error(R.drawable.default_comic)
                        .into(imageView);
            }

            @Override
            public int getItemCount() {
                return imageUrls != null ? imageUrls.size() : 0;
            }
        });
    }

}
