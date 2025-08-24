package com.example.test.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.animation.AnimationUnderline;
import com.example.test.entity.ComicDetail;
import com.example.test.repository.LoadCallbackComicDetail;
import com.example.test.service.ComicListChapterService;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class ComicDetailFragment extends Fragment {
    FlexboxLayout tagContainer;
    String slug = HomeFragment.slug;

    public static void addTags(Context context, FlexboxLayout container, List<ComicDetail.Breadcrumb> tags) {
        container.removeAllViews();

        for (ComicDetail.Breadcrumb tag : tags) {
            TextView textView = new TextView(context);
            textView.setText(tag.getName());
            textView.setTextColor(Color.parseColor("#BDBDBD"));
            textView.setBackgroundResource(R.drawable.chip_bg);
            textView.setPadding(24, 12, 24, 12);

            // Nếu tag quá dài thì cắt
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);

            // Layout params cho chip
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            textView.setLayoutParams(params);

            container.addView(textView);
        }
    }

    public static void setupChapterRecycler(RecyclerView recyclerChapters, List<ComicDetail.Chapter> chapters, Context context) {
        // 1. Gán LayoutManager
        recyclerChapters.setLayoutManager(new LinearLayoutManager(context));

        // 2. Tạo Adapter
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
                return new ChapterViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ChapterViewHolder vh = (ChapterViewHolder) holder;
                vh.tvChapterName.setText("Chapter " + chapters.get(position).getChapterName());
            }

            @Override
            public int getItemCount() {
                return chapters.size();
            }

            class ChapterViewHolder extends RecyclerView.ViewHolder {
                TextView tvChapterName;

                public ChapterViewHolder(@NonNull View itemView) {
                    super(itemView);
                    tvChapterName = itemView.findViewById(R.id.tvChapterName);
                }
            }
        };
        // 3. Gán Adapter
        recyclerChapters.setAdapter(adapter);

        // 4. Hiển thị RecyclerView
        recyclerChapters.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_comic, container, false);

        TextView tabDetail = view.findViewById(R.id.tabDetail);
        TextView tabChapter = view.findViewById(R.id.tabChapter);
        View underline = view.findViewById(R.id.underline);
        LinearLayout layoutDetail = view.findViewById(R.id.detailLayout);
        ScrollView containerScroll = view.findViewById(R.id.containerScroll);
        RecyclerView recyclerChapters = view.findViewById(R.id.recyclerChapters);
        tagContainer = view.findViewById(R.id.tagContainer);
        TextView content = view.findViewById(R.id.content);
        TextView status = view.findViewById(R.id.status);
        AnimationUnderline animation = new AnimationUnderline(underline, tabDetail, tabChapter);

        ComicListChapterService service = new ComicListChapterService();

        service.getComicDetail(slug, new LoadCallbackComicDetail() {
            @Override
            public void onLoadSuccess(ComicDetail comicDetail) {
                status.setText("Đang cập nhật");
                content.setText(comicDetail.content);
                addTags(requireContext(), tagContainer, comicDetail.breadcrumbs);
                setupChapterRecycler(recyclerChapters, comicDetail.chapters, requireContext());
                makeExpandable(content, containerScroll, 3, " Xem thêm", " Thu gọn");
            }

            @Override
            public void onLoadFailed(Throwable error) {
            }
        });

        tabDetail.setOnClickListener(v -> {
            layoutDetail.setVisibility(View.VISIBLE);
            recyclerChapters.setVisibility(View.GONE);
            animation.moveTo(tabDetail, true);

            tabDetail.setTextColor(Color.WHITE);
            tabChapter.setTextColor(Color.GRAY);
        });

        tabChapter.setOnClickListener(v -> {
            layoutDetail.setVisibility(View.GONE);
            recyclerChapters.setVisibility(View.VISIBLE);
            animation.moveTo(tabChapter, true);

            tabChapter.setTextColor(Color.WHITE);
            tabDetail.setTextColor(Color.GRAY);
        });

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity().findViewById(R.id.taskbar).setVisibility(View.VISIBLE);
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void makeExpandable(TextView textView, ScrollView containerScroll,
                                int maxLines, String expandText, String collapseText) {
        textView.post(() -> {
            int lineCount = textView.getLineCount();
            if (lineCount <= maxLines) {
                // Không cần nút xem thêm nếu ít hơn maxLines
                return;
            }

            // Ban đầu thu gọn
            textView.setMaxLines(maxLines);
            textView.setEllipsize(TextUtils.TruncateAt.END);

            // Tạo TextView nút toggle
            TextView toggleView = new TextView(textView.getContext());
            toggleView.setText(expandText);
            toggleView.setTextColor(Color.parseColor("#FF2D6C"));
            toggleView.setPadding(0, 8, 0, 0);
            toggleView.setOnClickListener(v -> {
                if (textView.getMaxLines() == maxLines) {
                    textView.setMaxLines(Integer.MAX_VALUE);
                    textView.setEllipsize(null);
                    toggleView.setText(collapseText);
                } else {
                    textView.setMaxLines(maxLines);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    toggleView.setText(expandText);
                    containerScroll.fullScroll(View.FOCUS_UP); // đưa scroll về trên khi thu gọn
                }
            });

            // Thêm nút ngay sau content
            ((LinearLayout) textView.getParent()).addView(toggleView,
                    ((LinearLayout) textView.getParent()).indexOfChild(textView) + 1);
        });
    }

}
