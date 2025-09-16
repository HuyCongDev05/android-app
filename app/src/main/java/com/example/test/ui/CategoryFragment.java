package com.example.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.service.CategoryComicService;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Map;

public class CategoryFragment extends Fragment {

    private FlexboxLayout flexLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acitvity_category, container, false);

        flexLayout = view.findViewById(R.id.flexCategories);

        CategoryComicService service = new CategoryComicService();
        service.getCategories(categoryMap -> {
            flexLayout.removeAllViews();

            for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
                String slug = entry.getKey();
                String name = entry.getValue();

                TextView tv = new TextView(requireContext());
                tv.setText(name);
                tv.setBackgroundResource(R.drawable.chip_ripple);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv.setPadding(10, 6, 10, 6);

                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(6, 6, 6, 6);
                tv.setLayoutParams(params);

                tv.setOnClickListener(v -> {
                    CategoryDetailFragment.slug = slug;
                    CategoryDetailFragment.name = name;
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootLayout, new CategoryDetailFragment())
                            .addToBackStack(null)
                            .commit();
                });

                flexLayout.addView(tv);
            }
        });

        return view;
    }
}

