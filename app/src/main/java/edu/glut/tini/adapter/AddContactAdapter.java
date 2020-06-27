package edu.glut.tini.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.glut.tini.widget.ActionContactShowListItem;

/**
 * @Author Ardien
 * @Date 6/27/2020 12:27 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class AddContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public AddContactAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddContactAdapterViewHolder(new ActionContactShowListItem(context,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    private static class AddContactAdapterViewHolder extends RecyclerView.ViewHolder{

        public AddContactAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
