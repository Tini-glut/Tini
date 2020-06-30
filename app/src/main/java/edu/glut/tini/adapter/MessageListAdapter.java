package edu.glut.tini.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;

import java.util.List;

import edu.glut.tini.widget.RecMessageItemView;
import edu.glut.tini.widget.SendMessageItemView;

/**
 * @author lhdigo
 * @date 2020/6/29 15:28
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<EMMessage> emMessageList;

    private int ITEM_TYPE_SEND_MESSAGE = 0;
    private int ITEM_TYPE_RECEIVE_MESSAGE = 1;

    public MessageListAdapter(Context context, List<EMMessage> emMessageList) {
        this.context = context;
        this.emMessageList = emMessageList;
    }

    public MessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (emMessageList.get(position).direct().equals(EMMessage.Direct.SEND)) {
            return ITEM_TYPE_SEND_MESSAGE;
        } else return ITEM_TYPE_RECEIVE_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SEND_MESSAGE) {
            return new SendMessageViewHolder(new SendMessageItemView(context, null));
        } else if (viewType == ITEM_TYPE_RECEIVE_MESSAGE) {
            return new ReceiveMessageViewHolder(new RecMessageItemView(context, null));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Boolean showTimeStamp = isShowTimeStamp(position);
        if (getItemViewType(position)==ITEM_TYPE_SEND_MESSAGE){
            SendMessageItemView sendMessageItemView = (SendMessageItemView) holder.itemView;
            sendMessageItemView.bindView(emMessageList.get(position),showTimeStamp);
        }else {
            RecMessageItemView recMessageItemView =(RecMessageItemView) holder.itemView;
            recMessageItemView.bindView(emMessageList.get(position),showTimeStamp);
        }
    }

    private Boolean isShowTimeStamp(int position) {
        boolean showTimeStamp =true;
        if(position>0){
            showTimeStamp = !DateUtils.isCloseEnough(emMessageList.get(position).getMsgTime(),
                    emMessageList.get(position-1).getMsgTime());
        }
        return showTimeStamp;
    }

    @Override
    public int getItemCount() {
        return emMessageList.size();
    }

    class SendMessageViewHolder extends RecyclerView.ViewHolder {
        public SendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ReceiveMessageViewHolder extends RecyclerView.ViewHolder {

        public ReceiveMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
