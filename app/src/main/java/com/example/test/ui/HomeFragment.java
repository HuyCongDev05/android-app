package com.example.test.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.example.test.service.FollowComicService;
import com.example.test.service.LoginService;

import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        ImageView iconSearch = view.findViewById(R.id.iconSearch);
        EditText searchInput = view.findViewById(R.id.searchInput);
        View searchOverlay = view.findViewById(R.id.searchOverlay);
        FollowComicService followComicService = new FollowComicService();
        followComicService.getFollowComic(LoginService.userId);

        iconSearch.setOnClickListener(v -> {
            searchInput.setVisibility(View.VISIBLE);
            searchOverlay.setVisibility(View.VISIBLE);
            searchInput.requestFocus();

            // Hiện bàn phím
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
        });

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = v.getText().toString().trim();
                if (!query.isEmpty()) {
                    SearchUI searchUI = new SearchUI();
                    searchUI.performSearch(query);
                    searchInput.setText("");
                    requireActivity().findViewById(R.id.taskbar).setVisibility(View.GONE);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(false)
                            .replace(R.id.fragment_container, searchUI)
                            .addToBackStack(null)
                            .commit();
                }
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
                searchInput.setVisibility(View.GONE);
                searchOverlay.setVisibility(View.GONE);
                return true;
            }
            return false;
        });

        searchOverlay.setOnClickListener(v -> {
            searchInput.setVisibility(View.GONE);
            searchOverlay.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
        });
        if (DataCache.comicMap != null) {
            ComicListBook(DataCache.comicMap, view);
        }
        return view;
    }

    private View ComicView(Comic comic, ViewGroup parent) {
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
            ComicDetailFragment.nameComic = comic.getName();
            ComicDetailFragment.slug = comic.getSlug();
            ComicDetailFragment.urlComic = comic.getImageUrl();
            ComicDetailFragment detailFragment = new ComicDetailFragment();
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.GONE);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(false)
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
                View comicView = ComicView(comic, comicNewListLayout);
                comicNewListLayout.addView(comicView);
            }
        }

        // lấy list truyên đã đề xuất
        List<Comic> proposeComics = map.get("proposeComics");
        if (proposeComics != null && !proposeComics.isEmpty()) {
            LinearLayout comicProposeListLayout = root.findViewById(R.id.ProposeList);
            for (Comic comic : proposeComics) {
                View comicView = ComicView(comic, comicProposeListLayout);
                comicProposeListLayout.addView(comicView);
            }
        }

        // lấy list truyên đã hoàn thành
        List<Comic> finishedComics = map.get("finishedComics");
        if (finishedComics != null && !finishedComics.isEmpty()) {
            LinearLayout comicFinishedListLayout = root.findViewById(R.id.ComicFinishedList);
            for (Comic comic : finishedComics) {
                View comicView = ComicView(comic, comicFinishedListLayout);
                comicFinishedListLayout.addView(comicView);
            }
        }
    }
}
