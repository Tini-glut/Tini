package edu.glut.tini.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.List;

import edu.glut.tini.ui.activity.ChatActivity;
import edu.glut.tini.widget.ConversationListItemView;

public class ConversationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<EMConversation> conversations = new ArrayList<>();
    public ConversationListAdapter(Context context, List<EMConversation> conversations){
        this.context = context;
        this.conversations=conversations;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationListItemViewHolder(new ConversationListItemView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationListItemView conversationListItemView = (ConversationListItemView) holder.itemView;
        conversationListItemView.bindView(conversations.get(position));
        conversationListItemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("userName", conversations.get(position).conversationId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ConversationListItemViewHolder extends RecyclerView.ViewHolder{

        public ConversationListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
