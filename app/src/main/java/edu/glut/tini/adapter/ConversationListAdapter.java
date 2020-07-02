package edu.glut.tini.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.List;

import edu.glut.tini.R;
import edu.glut.tini.ui.activity.ChatActivity;
import edu.glut.tini.widget.ConversationListItemView;

public class ConversationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<EMConversation> conversations;

    public ConversationListAdapter(Context context, List<EMConversation> conversations) {
        this.context = context;
        this.conversations = conversations;
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
            String username = conversations.get(position).conversationId();
            intent.putExtra("userName", username);
            context.startActivity(intent);
        });

        conversationListItemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.long_click_popup_conversation_item_, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.delete_conversation:
                        EMClient.getInstance().chatManager().deleteConversation(conversations.get(position).conversationId(), false);
                        conversations.remove(conversations.get(position));
                        notifyDataSetChanged();
                        break;
                    case R.id.delete_conversation_and_message:
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                        builder
                                .setMessage("删除后，聊天记录将会被清空!")
                                .setNegativeButton(context.getString(R.string.cancel),null)
                                .setPositiveButton(context.getString(android.R.string.yes),(dialog, which) -> {
                                    EMClient.getInstance().chatManager().deleteConversation(conversations.get(position).conversationId(), false);
                                    conversations.remove(conversations.get(position));
                                    notifyDataSetChanged();
                                });
                        builder.show();

                        break;
                }
                return true;
            });

            popupMenu.show();
            return true;
        });


    }


    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ConversationListItemViewHolder extends RecyclerView.ViewHolder {

        public ConversationListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
