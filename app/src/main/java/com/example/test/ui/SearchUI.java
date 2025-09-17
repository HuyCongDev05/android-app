package com.example.test.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.entity.Comic;
import com.example.test.repository.LoadCallBackSearchComic;
import com.example.test.service.ComicSearchService;

import java.util.List;

public class SearchUI extends Fragment {

    private RecyclerView recyclerSearchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        EditText searchInputResult = view.findViewById(R.id.searchInputResult);
        recyclerSearchResult = view.findViewById(R.id.recyclerSearchResult);
        View searchOverlay = view.findViewById(R.id.searchOverlay);

        searchInputResult.setText("");

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.VISIBLE);
        });

        searchInputResult.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchOverlay.setVisibility(View.VISIBLE);
            }
        });
        // Lắng nghe sự kiện tìm kiếm
        searchInputResult.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String q = v.getText().toString().trim();
                if (!q.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) requireContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchInputResult.getWindowToken(), 0);
                    performSearch(q);
                } else {
                    Toast.makeText(requireContext(), "Nhập từ khóa để tìm kiếm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        searchOverlay.setOnClickListener(v -> {
            searchOverlay.setVisibility(View.GONE);
            View currentFocus = requireActivity().getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
                InputMethodManager imm = (InputMethodManager) requireContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });
        return view;
    }

    public void performSearch(String query) {
        ComicSearchService service = new ComicSearchService();

        service.searchComic(query, new LoadCallBackSearchComic() {
            @Override
            public void onSuccess(List<Comic> comics) {
                setupSearchRecycler(recyclerSearchResult, comics, (FragmentActivity) requireContext());
            }

            @Override
            public void onFailed(Throwable ex) {
                System.out.println("Lỗi tìm kiếm: " + ex.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void setupSearchRecycler(
            RecyclerView recyclerSearchResult,
            List<Comic> dataList,
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
                    tv.setText("Không tìm thấy truyện");
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
            recyclerSearchResult.setAdapter(new Adapter<>() {
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
                            .into(img);
                    itemComic.setOnClickListener(v -> {
                        ComicDetailFragment.nameComic = comic.getName();
                        ComicDetailFragment.slug = comic.getSlug();
                        ComicDetailFragment.urlComic = comic.getImageUrl();

                        ComicDetailFragment detailFragment = new ComicDetailFragment();
                        requireActivity().getSupportFragmentManager()
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
