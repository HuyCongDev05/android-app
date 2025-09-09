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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

public class SearchUI extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        EditText searchInputResult = view.findViewById(R.id.searchInputResult);
        RecyclerView recyclerSearchResult = view.findViewById(R.id.recyclerSearchResult);
        View searchOverlay = view.findViewById(R.id.searchOverlay);

        searchInputResult.setText("");

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.VISIBLE);
        });

        // Lắng nghe sự kiện tìm kiếm
        searchInputResult.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String q = v.getText().toString().trim();
                if (!q.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) requireContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchInputResult.getWindowToken(), 0);
                } else {
                    Toast.makeText(requireContext(), "Nhập từ khóa để tìm kiếm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        // RecyclerView hiển thị 3 cột
        recyclerSearchResult.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        searchOverlay.setOnClickListener(v -> {
            searchOverlay.setVisibility(View.GONE);
            // Lấy view hiện đang focus
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
}
