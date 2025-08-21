package com.example.test.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.animation.AnimationUnderline;

public class ComicDetailFragment extends Fragment {

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

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.VISIBLE);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

}
