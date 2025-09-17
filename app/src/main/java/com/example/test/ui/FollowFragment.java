package com.example.test.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
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
import com.example.test.entity.ComicDetail;
import com.example.test.repository.DataCache;
import com.example.test.repository.LoadCallbackComicDetail;
import com.example.test.service.ComicDetailService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FollowFragment extends Fragment {
    RecyclerView recyclerSearchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_follow, container, false);
        recyclerSearchResult = rootView.findViewById(R.id.recyclerSearchResult);
        getComicDetailListSequential(DataCache.listFollowComic);
        return rootView;
    }

    public void getComicDetailListSequential(List<String> slugs) {
        if (slugs == null || slugs.isEmpty()) {
            setupSearchRecycler(recyclerSearchResult, new ArrayList<>(), requireActivity());
            return;
        }
        ComicDetailService service = new ComicDetailService();

        DataCache.listComicDetail = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(slugs.size());

        for (String slug : slugs) {
            service.getComicDetail(slug, new LoadCallbackComicDetail() {
                @Override
                public void onLoadSuccess(ComicDetail comicDetail) {
                    DataCache.listComicDetail.add(comicDetail);

                    if (counter.decrementAndGet() == 0) {
                        setupSearchRecycler(recyclerSearchResult, DataCache.listComicDetail, requireActivity());
                    }
                }

                @Override
                public void onLoadFailed(Throwable error) {
                    if (counter.decrementAndGet() == 0) {
                        setupSearchRecycler(recyclerSearchResult, DataCache.listComicDetail, requireActivity());
                    }
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    public void setupSearchRecycler(
            RecyclerView recyclerSearchResult,
            List<ComicDetail> dataList,
            FragmentActivity activity) {

        if (dataList == null || dataList.isEmpty()) {
            recyclerSearchResult.setLayoutManager(new GridLayoutManager(activity, 1));
            recyclerSearchResult.setAdapter(new RecyclerView.Adapter<>() {
                @Override
                public int getItemCount() {
                    return 1;
                }

                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    TextView tv = new TextView(parent.getContext());
                    tv.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));
                    tv.setText("Chưa có theo dõi truyện");
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(16);
                    return new RecyclerView.ViewHolder(tv) {
                    };
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                }
            });
        } else {
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
                    ComicDetail comicDetail = dataList.get(position);

                    ImageView img = holder.itemView.findViewById(R.id.ImgComic);
                    TextView name = holder.itemView.findViewById(R.id.NameComic);
                    LinearLayout itemComic = holder.itemView.findViewById(R.id.itemComic);

                    name.setText(comicDetail.name);
                    Glide.with(holder.itemView.getContext())
                            .load(comicDetail.image)
                            .placeholder(R.drawable.default_comic)
                            .error(R.drawable.default_comic)
                            .into(img);

                    itemComic.setOnClickListener(v -> {
                        ComicDetailFragment.nameComic = comicDetail.name;
                        ComicDetailFragment.slug = !comicDetail.breadcrumbs.isEmpty()
                                ? comicDetail.slug
                                : "";
                        ComicDetailFragment.urlComic = comicDetail.image;

                        ComicDetailFragment detailFragment = new ComicDetailFragment();
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment_container, detailFragment)
                                .addToBackStack(null)
                                .commit();
                    });
                }
            });
        }
    }
}
