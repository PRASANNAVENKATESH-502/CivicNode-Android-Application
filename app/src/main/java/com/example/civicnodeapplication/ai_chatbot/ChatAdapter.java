package com.example.civicnodeapplication.ai_chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civicnodeapplication.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);

        if (message.isUser()) {
            holder.senderLayout.setVisibility(View.VISIBLE);
            holder.receiverLayout.setVisibility(View.GONE);
            holder.tvSenderMessage.setText(message.getMessage());
        } else {
            holder.senderLayout.setVisibility(View.GONE);
            holder.receiverLayout.setVisibility(View.VISIBLE);
            holder.tvReceiverMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout senderLayout, receiverLayout;
        TextView tvSenderMessage, tvReceiverMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderLayout = itemView.findViewById(R.id.senderLayout);
            receiverLayout = itemView.findViewById(R.id.receiverLayout);
            tvSenderMessage = itemView.findViewById(R.id.tvSenderMessage);
            tvReceiverMessage = itemView.findViewById(R.id.tvReceiverMessage);
        }
    }
}
