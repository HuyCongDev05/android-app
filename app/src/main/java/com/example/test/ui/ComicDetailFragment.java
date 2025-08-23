package com.example.test.ui;

import android.content.Context;
import android.graphics.Color;
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

import com.example.test.R;
import com.example.test.animation.AnimationUnderline;
import com.example.test.entity.ComicDetail;
import com.google.android.flexbox.FlexboxLayout;

public class ComicDetailFragment extends Fragment {
    FlexboxLayout tagContainer;
    String slug = HomeFragment.slug;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_comic, container, false);
        TextView tabDetail = view.findViewById(R.id.tabDetail);
        TextView tabChapter = view.findViewById(R.id.tabChapter);
        View underline = view.findViewById(R.id.underline);
        new AnimationUnderline(underline, tabDetail, tabChapter);

        tagContainer = view.findViewById(R.id.tagContainer);

        String[] tags = {"Hành động", "Hài hước", "Phiêu lưu", "Xuyên không", "Ngôn tình"};
        addTags(requireContext(), tagContainer, tags);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.VISIBLE);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }
    public static void showTags(Context context, ViewGroup container, ComicDetail detail) {
        container.removeAllViews();

        for (ComicDetail.Breadcrumb breadcrumb : detail.breadcrumbs) {
            TextView tagView = new TextView(context);
            tagView.setText(breadcrumb.name);
            tagView.setPadding(16, 8, 16, 8);
            tagView.setBackgroundResource(R.drawable.tag_background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            tagView.setLayoutParams(params);

            container.addView(tagView);
        }
    }
    public static void addTags(Context context, FlexboxLayout container, String[] tags) {
        container.removeAllViews();
        for (String tag : tags) {
            TextView textView = new TextView(context);
            textView.setText(tag);
            textView.setTextColor(Color.parseColor("#BDBDBD"));
            textView.setBackgroundResource(R.drawable.chip_bg);
            textView.setPadding(24, 12, 24, 12);

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            textView.setLayoutParams(params);

            container.addView(textView);
        }
    }

}
