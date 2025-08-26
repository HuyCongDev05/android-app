package com.example.test.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.animation.AnimationUnderline;
import com.example.test.entity.ComicDetail;
import com.example.test.repository.LoadCallbackComicDetail;
import com.example.test.service.ComicListChapterService;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
import java.util.Objects;

public class ComicDetailFragment extends Fragment {
    FlexboxLayout tagContainer;
    String slug = HomeFragment.slug;
    String nameComic = HomeFragment.nameComic;
    String urlComic = HomeFragment.urlComic;

    public static void addTags(Context context, FlexboxLayout container, List<ComicDetail.Breadcrumb> tags) {
        container.removeAllViews();

        for (ComicDetail.Breadcrumb tag : tags) {
            TextView textView = new TextView(context);
            textView.setText(tag.getName());
            textView.setTextColor(Color.parseColor("#BDBDBD"));
            textView.setBackgroundResource(R.drawable.chip_bg);
            textView.setPadding(12, 12, 12, 12);

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
        //  Gán LayoutManager
        recyclerChapters.setLayoutManager(new LinearLayoutManager(context));

        //  Tạo Adapter
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
                vh.tvChapterName.setTextColor(Color.WHITE);
            }

            @Override
            public int getItemCount() {
                return chapters.size();
            }

            class ChapterViewHolder extends RecyclerView.ViewHolder {
                final TextView tvChapterName;

                public ChapterViewHolder(@NonNull View itemView) {
                    super(itemView);
                    tvChapterName = itemView.findViewById(R.id.tvChapterName);
                }
            }
        };

        //  Gán Adapter
        recyclerChapters.setAdapter(adapter);

        //  Hiển thị RecyclerView
        recyclerChapters.setVisibility(View.VISIBLE);

        //  Thêm gạch chân chapter
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider_gray)));
        recyclerChapters.addItemDecoration(divider);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_comic, container, false);

        View spinnerOverlay = view.findViewById(R.id.spinner_overlay_detail_comic);
        ImageView spinner = view.findViewById(R.id.spinner);
        Animation rotateAnim = AnimationUtils.loadAnimation(getContext(), R.anim.spinner_rotate_anim);
        TextView tabDetail = view.findViewById(R.id.tabDetail);
        TextView tabChapter = view.findViewById(R.id.tabChapter);
        TextView ComicName = view.findViewById(R.id.nameComic);
        View underline = view.findViewById(R.id.underline);
        LinearLayout layoutDetail = view.findViewById(R.id.detailLayout);
        ScrollView containerScroll = view.findViewById(R.id.containerScroll);
        RecyclerView recyclerChapters = view.findViewById(R.id.recyclerChapters);
        tagContainer = view.findViewById(R.id.tagContainer);
        TextView content = view.findViewById(R.id.content);
        TextView status = view.findViewById(R.id.status);
        ImageView headerImage = view.findViewById(R.id.headerImage);
        Button btnFollow = view.findViewById(R.id.btnFollow);
        Button btnViewStart = view.findViewById(R.id.btnViewStart);

        ComicName.setText(nameComic);
        AnimationUnderline animation = new AnimationUnderline(underline, tabDetail, tabChapter);
        ComicListChapterService service = new ComicListChapterService();

        spinnerOverlay.setVisibility(View.VISIBLE);
        spinner.startAnimation(rotateAnim);

        service.getComicDetail(slug, new LoadCallbackComicDetail() {
            @Override
            public void onLoadSuccess(ComicDetail comicDetail) {
                spinnerOverlay.setVisibility(View.GONE);
                spinner.clearAnimation();

                Glide.with(requireContext())
                        .load(urlComic)
                        .placeholder(R.drawable.default_comic)
                        .into(headerImage);
                status.setText("Đang cập nhật");
                content.setText(comicDetail.content);
                addTags(requireContext(), tagContainer, comicDetail.breadcrumbs);
                setupChapterRecycler(recyclerChapters, comicDetail.chapters, requireContext());
                makeExpandable(content, containerScroll, 3, " Xem thêm", " Thu gọn");
            }

            @Override
            public void onLoadFailed(Throwable error) {
                spinnerOverlay.setVisibility(View.GONE);
                spinner.clearAnimation();
            }
        });

        tabDetail.setOnClickListener(v -> {
            layoutDetail.setVisibility(View.VISIBLE);
            recyclerChapters.setVisibility(View.GONE);

            tabDetail.setTextColor(Color.WHITE);
            tabChapter.setTextColor(Color.GRAY);

            animation.moveTo(tabDetail, true);
        });

        tabChapter.setOnClickListener(v -> {
            layoutDetail.setVisibility(View.GONE);
            recyclerChapters.setVisibility(View.VISIBLE);

            tabChapter.setTextColor(Color.WHITE);
            tabDetail.setTextColor(Color.GRAY);

            animation.moveTo(tabChapter, true);
        });

        btnFollow.setOnClickListener(v -> {

            btnFollow.setText("Đang theo dõi");
        });

        btnViewStart.setOnClickListener(v -> {

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
            if (lineCount <= maxLines) return;

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
