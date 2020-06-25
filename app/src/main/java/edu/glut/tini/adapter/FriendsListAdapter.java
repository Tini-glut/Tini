package edu.glut.tini.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.glut.tini.data.item.FriendsListItem;
import edu.glut.tini.ui.activity.ChatActivity;
import edu.glut.tini.widget.FriendsListItemView;

public class FriendsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FriendsListItem> friendsListItems = new ArrayList<>();

    public FriendsListAdapter(Context context) {
        this.context = context;
    }

    public FriendsListAdapter(List<FriendsListItem> friendsListItems) {
        this.friendsListItems = friendsListItems;
    }

    public FriendsListAdapter(Context context, List<FriendsListItem> friendsListItems) {
        this.context = context;
        this.friendsListItems = friendsListItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsListItemViewHolder(new FriendsListItemView(context, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendsListItemView friendsListItemView = (FriendsListItemView) holder.itemView;
        friendsListItemView.bindView(friendsListItems.get(position));
        //短按事件
        friendsListItemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("userName", friendsListItems.get(position).getUserName());
            context.startActivity(intent);
        });
        //长按事件
        friendsListItemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("删除好友")
                    .setMessage("你确定要删除此好友吗")
                    .setNegativeButton("NO", null)
                    .setPositiveButton("YES", (dialog, which) -> {
                        delFriend(friendsListItems.get(position).getUserName());
                    })
                    .show();
            return true;
        });
    }

    //删除好友操作
    private void delFriend(String userName) {

    }

    @Override
    public int getItemCount() {
        System.out.println(friendsListItems.size());
        return friendsListItems.size();
    }


    class FriendsListItemViewHolder extends RecyclerView.ViewHolder {

        public FriendsListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
