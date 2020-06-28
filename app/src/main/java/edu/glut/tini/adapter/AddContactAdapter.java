package edu.glut.tini.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.glut.tini.data.entity.ContactItem;
import edu.glut.tini.widget.ActionContactShowListItem;

/**
 * @Author Ardien
 * @Date 6/27/2020 12:27 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class AddContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ContactItem> data;

    public AddContactAdapter(Context context, List<ContactItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddContactAdapterViewHolder(new ActionContactShowListItem(context,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ActionContactShowListItem itemView = (ActionContactShowListItem) holder.itemView;
        itemView.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class AddContactAdapterViewHolder extends RecyclerView.ViewHolder{

        public AddContactAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }
}
