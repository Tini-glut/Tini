package edu.glut.tini.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.ui.activity.ChatActivity;
import edu.glut.tini.widget.SearchResultItem;

/**
 * @Author Ardien
 * @Date 7/8/2020 4:53 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class SearchResultItemAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Contacts> data;

    public SearchResultItemAdapter(Context context, List<Contacts> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchListItemViewHolder(new SearchResultItem(context,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchResultItem item = (SearchResultItem) holder.itemView;
        item.bindView(data.get(position));
        item.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            String username = data.get(position).getContactsFriendUsername();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.putExtra("userName", username);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchListItemViewHolder extends RecyclerView.ViewHolder {

        public SearchListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
