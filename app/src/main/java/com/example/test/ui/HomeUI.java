package com.example.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.entity.Comic;
import com.example.test.repository.DataCache;
import com.example.test.ui.custom.NavigationHelper;

import java.util.HashMap;
import java.util.List;

public class HomeUI extends AppCompatActivity {
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (DataCache.comicMap != null) {
            ComicListBook(DataCache.comicMap);
        }
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnSettings = findViewById(R.id.btn_settings);

        navigationHelper = new NavigationHelper(this, btnHome, btnSettings);
        navigationHelper.setActiveButton(btnHome.getId());

        btnHome.setOnClickListener(v -> {
            navigationHelper.setActiveButton(btnHome.getId());
            // load layout
        });

        btnSettings.setOnClickListener(v -> {
            navigationHelper.setActiveButton(btnSettings.getId());
            // load layout
        });
    }

    private View createComicView(Comic comic, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_comic, parent, false);

        ImageView imageView = view.findViewById(R.id.ImgComic);
        TextView nameView = view.findViewById(R.id.NameComic);

        String comicName = comic.getName();
        if (comicName == null || comicName.trim().isEmpty()) {
            comicName = "Tên truyện đang cập nhật...";
        }
        nameView.setText(comicName);

        Glide.with(this)
                .load(comic.getImageUrl())
                .placeholder(R.drawable.default_comic)
                .error(R.drawable.default_comic)
                .into(imageView);
        return view;
    }

    public void ComicListBook(HashMap<String, List<Comic>> map) {

        // Xử lý list ComicNew
        List<Comic> newComics = map.get("newComics");
        if (newComics != null && !newComics.isEmpty()) {
            LinearLayout comicNewListLayout = findViewById(R.id.ComicNewList);
            for (Comic comic : newComics) {
                View comicView = createComicView(comic, comicNewListLayout);
                comicNewListLayout.addView(comicView);
            }
        }

        // Xử lý list ComicPropose
        List<Comic> proposeComics = map.get("proposeComics");
        if (proposeComics != null && !proposeComics.isEmpty()) {
            LinearLayout comicProposeListLayout = findViewById(R.id.ProposeList);
            for (Comic comic : proposeComics) {
                View comicView = createComicView(comic, comicProposeListLayout);
                comicProposeListLayout.addView(comicView);
            }
        }

        // Xử lý list finishedComics
        List<Comic> finishedComics = map.get("finishedComics");
        if (finishedComics != null && !finishedComics.isEmpty()) {
            LinearLayout comicFinishedListLayout = findViewById(R.id.ComicFinishedList);
            for (Comic comic : finishedComics) {
                View comicView = createComicView(comic, comicFinishedListLayout);
                comicFinishedListLayout.addView(comicView);
            }
        }
    }
}