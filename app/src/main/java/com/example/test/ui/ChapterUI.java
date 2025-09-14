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
import com.example.test.entity.ComicDetail;
import com.example.test.service.ChapterComicService;
import com.example.test.util.ChapterUtils;

import java.util.List;
import java.util.stream.IntStream;

public class ChapterUI extends AppCompatActivity {

    public static String chapter;
    int currentIndex = 0;
    public static List<ComicDetail.Chapter> chapters;

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
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        ChapterComicService service = new ChapterComicService();
        service.handleImagesChapterComic(chapter, this::loadImages);

        if (chapter.equals(chapters.get(0).getChapterName())) {
            btnBackChapter.setImageResource(R.drawable.icon_back_chapter_max);
        }
        currentIndex = IntStream.range(0, chapters.size())
                .filter(i -> chapters.get(i).getChapterName().equals(chapter))
                .findFirst()
                .orElse(0);

        btnBackChapter.setOnClickListener(v -> {
            if (currentIndex > 0) currentIndex--;
            service.handleImagesChapterComic(chapters.get(currentIndex).getChapterName(), this::loadImages);
            chapterName.setText("Chapter " + chapters.get(currentIndex).getChapterName());
            btnBackChapter.setImageResource(currentIndex == 0 ? R.drawable.icon_back_chapter_max : R.drawable.icon_back_chapter);
            btnNextChapter.setImageResource(currentIndex == chapters.size()-1 ? R.drawable.icon_next_chapter_max : R.drawable.icon_next_chapter);
        });

        btnNextChapter.setOnClickListener(v -> {
            if (currentIndex < chapters.size()-1) currentIndex++;
            service.handleImagesChapterComic(chapters.get(currentIndex).getChapterName(), this::loadImages);
            chapterName.setText("Chapter " + chapters.get(currentIndex).getChapterName());
            btnBackChapter.setImageResource(currentIndex == 0 ? R.drawable.icon_back_chapter_max : R.drawable.icon_back_chapter);
            btnNextChapter.setImageResource(currentIndex == chapters.size()-1 ? R.drawable.icon_next_chapter_max : R.drawable.icon_next_chapter);
        });

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
                        .override(Target.SIZE_ORIGINAL)
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
