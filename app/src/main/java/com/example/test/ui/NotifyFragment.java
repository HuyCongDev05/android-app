package com.example.test.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.entity.Message;
import com.example.test.repository.DataCache;
import com.example.test.service.MessageService;

import java.util.List;

public class NotifyFragment extends Fragment {

    private RecyclerView rvMessages;

    @Nullable
    @Override
    public View onCreateView(@NonNull android.view.LayoutInflater inflater,
                             @Nullable android.view.ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notify, container, false);

        rvMessages = view.findViewById(R.id.rvMessages);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        displayMessages(DataCache.messages);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMessages();
    }

    private void loadMessages() {
        MessageService.getMessage()
                .thenAccept(messages -> {
                    DataCache.messages = messages;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> displayMessages(messages));
                    }
                })
                .exceptionally(ex -> {
                    System.out.println("Lỗi loadMessages: " + ex.getMessage());
                    return null;
                });
    }

    private void displayMessages(List<Message> messages) {
        if (messages == null || rvMessages == null || getView() == null) return;

        FrameLayout overlay = getView().findViewById(R.id.messageOverlay);
        TextView tvFull = getView().findViewById(R.id.tvFullMessage);

        overlay.setOnClickListener(v -> overlay.setVisibility(View.GONE));

        rvMessages.setAdapter(new RecyclerView.Adapter<>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setBackgroundResource(R.drawable.bg_message_item);
                tv.setSingleLine(true);
                tv.setEllipsize(android.text.TextUtils.TruncateAt.END);
                tv.setTextColor(0xFFFF4D8C);
                tv.setTextSize(16f);

                int padding = (int) (12 * parent.getResources().getDisplayMetrics().density);
                tv.setPadding(padding, padding, padding, padding);

                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, padding);
                tv.setLayoutParams(params);

                return new RecyclerView.ViewHolder(tv) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Message msg = messages.get(position);
                TextView tv = (TextView) holder.itemView;
                tv.setText(msg.getMessage());

                tv.setOnClickListener(v -> {
                    tvFull.setText(msg.getMessage());
                    overlay.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        });
    }

}
