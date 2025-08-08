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

import java.util.HashMap;
import java.util.List;

public class HomeUI extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    private View createComicView(Comic comic) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_comic, null);

        ImageView imageView = view.findViewById(R.id.ImgComic);
        TextView nameView = view.findViewById(R.id.NameComic);

        nameView.setText(comic.getComicName());

        Glide.with(this)
                .load(comic.getComicImage())
                .into(imageView);

        return view;
    }
    public void ComicListBook(HashMap<String , List<Comic>> List) {

        // Xử lý list ComicNew
        new Thread(() -> {
            List<Comic> newComics = List.get("NewComic");
            runOnUiThread(() -> {
                LinearLayout comicNewListLayout = findViewById(R.id.ComicNewList);
                for (Comic comic : newComics) {
                    View comicView = createComicView(comic);
                    comicNewListLayout.addView(comicView);
                }
            });
        }).start();

    // Xử lý list ComicPropose
        new Thread(() -> {
            List<Comic> proposeComics = List.get("ComicPropose");

            runOnUiThread(() -> {
                LinearLayout comicProposeListLayout = findViewById(R.id.ProposeList);
                for (Comic comic : proposeComics) {
                    View comicView = createComicView(comic);
                    comicProposeListLayout.addView(comicView);
                }
            });
        }).start();
    }
}

