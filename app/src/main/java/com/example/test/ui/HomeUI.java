package com.example.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.entity.Comic;
import com.example.test.repository.ComicListRepository;

import java.util.HashMap;
import java.util.List;

public class HomeUI extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ComicListRepository.loadComicsAsync(this);
    }
    private View createComicView(Comic comic) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_comic, null);

        ImageView imageView = view.findViewById(R.id.ImgComic);
        TextView nameView = view.findViewById(R.id.NameComic);

        String comicName = comic.getName();
        if (comicName == null || comicName.trim().isEmpty()) {
            comicName = "Tên truyện đang cập nhật...";
        }
        nameView.setText(comicName);

        Glide.with(this)
                .load(comic.getImageUrl())
                .into(imageView);

        return view;
    }
    public void ComicListBook(HashMap<String, List<Comic>> map) {
        // Xử lý list ComicNew
        List<Comic> newComics = map.get("newComics");
        if (newComics != null && !newComics.isEmpty()) {
            LinearLayout comicNewListLayout = findViewById(R.id.ComicNewList);
            for (Comic comic : newComics) {
                View comicView = createComicView(comic);
                comicNewListLayout.addView(comicView);
            }
        }

        // Xử lý list ComicPropose
        List<Comic> proposeComics = map.get("proposeComics");
        if (proposeComics != null && !proposeComics.isEmpty()) {
            LinearLayout comicProposeListLayout = findViewById(R.id.ProposeList);
            for (Comic comic : proposeComics) {
                View comicView = createComicView(comic);
                comicProposeListLayout.addView(comicView);
            }
        }
    }
}

