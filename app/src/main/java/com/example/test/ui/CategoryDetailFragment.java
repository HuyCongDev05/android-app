package com.example.test.ui;


import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.entity.Comic;
import com.example.test.service.CategoryComicService;

import java.util.List;

public class CategoryDetailFragment extends Fragment {
    public static String slug;
    public static String name;
    RecyclerView recyclerSearchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category_detail, container, false);
        recyclerSearchResult = view.findViewById(R.id.recyclerSearchResult);
        TextView title = view.findViewById(R.id.titleCategoryDetail);
        title.setText(name);
        getComicDetailListSequential(slug);

        return view;
    }

    public void getComicDetailListSequential(String slug) {
        CategoryComicService service = new CategoryComicService();

        service.getCategoryDetail(slug, categoryComicDetail -> setupSearchRecycler(recyclerSearchResult, categoryComicDetail, requireActivity()));
    }

    @SuppressLint("SetTextI18n")
    public void setupSearchRecycler(
            RecyclerView recyclerSearchResult,
            List<Comic> dataList,
            FragmentActivity activity) {

        recyclerSearchResult.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerSearchResult.setAdapter(new RecyclerView.Adapter<>() {
            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_comic, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Comic comic = dataList.get(position);

                ImageView img = holder.itemView.findViewById(R.id.ImgComic);
                TextView name = holder.itemView.findViewById(R.id.NameComic);
                LinearLayout itemComic = holder.itemView.findViewById(R.id.itemComic);

                name.setText(comic.getName());
                Glide.with(holder.itemView.getContext())
                        .load(comic.getImageUrl())
                        .placeholder(R.drawable.default_comic)
                        .error(R.drawable.default_comic)
                        .into(img);

                itemComic.setOnClickListener(v -> {
                    ComicDetailFragment detailFragment = new ComicDetailFragment();
                    ComicDetailFragment.nameComic = comic.getName();
                    ComicDetailFragment.slug = comic.getSlug();
                    ComicDetailFragment.urlComic = comic.getImageUrl();

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, detailFragment)
                            .addToBackStack(null)
                            .commit();
                });
            }
        });
    }

}
