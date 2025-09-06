package com.example.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.entity.Comic;
import com.example.test.repository.DataCache;

import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    public static String slug;
    public static String nameComic;
    public static String urlComic;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home, container, false);

        if (DataCache.comicMap != null) {
            ComicListBook(DataCache.comicMap, root);
        }

        return root;
    }

    private View createComicView(Comic comic, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
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

        view.setOnClickListener(v -> {
            nameComic = comic.getName();
            slug = comic.getSlug();
            urlComic = comic.getImageUrl();
            ComicDetailFragment detailFragment = new ComicDetailFragment();
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.GONE);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(false) // tắt tối ưu hóa
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }

    public void ComicListBook(HashMap<String, List<Comic>> map, View root) {
        // lấy list truyên mới
        List<Comic> newComics = map.get("newComics");
        if (newComics != null && !newComics.isEmpty()) {
            LinearLayout comicNewListLayout = root.findViewById(R.id.ComicNewList);
            for (Comic comic : newComics) {
                View comicView = createComicView(comic, comicNewListLayout);
                comicNewListLayout.addView(comicView);
            }
        }

        // lấy list truyên đã đề xuất
        List<Comic> proposeComics = map.get("proposeComics");
        if (proposeComics != null && !proposeComics.isEmpty()) {
            LinearLayout comicProposeListLayout = root.findViewById(R.id.ProposeList);
            for (Comic comic : proposeComics) {
                View comicView = createComicView(comic, comicProposeListLayout);
                comicProposeListLayout.addView(comicView);
            }
        }

        // lấy list truyên đã hoàn thành
        List<Comic> finishedComics = map.get("finishedComics");
        if (finishedComics != null && !finishedComics.isEmpty()) {
            LinearLayout comicFinishedListLayout = root.findViewById(R.id.ComicFinishedList);
            for (Comic comic : finishedComics) {
                View comicView = createComicView(comic, comicFinishedListLayout);
                comicFinishedListLayout.addView(comicView);
            }
        }
    }
}
