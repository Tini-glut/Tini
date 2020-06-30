package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;


import java.util.Date;

import edu.glut.tini.R;


public class ConversationListItemView extends RelativeLayout {
    private TextView userNameTextView;
    private TextView firstLetterTextView;
    private TextView timeTextView;
    private TextView unreadCountTextView;
    public ConversationListItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.view_conversation_item, this);
        userNameTextView = findViewById(R.id.list_label_username);
        firstLetterTextView = findViewById(R.id.list_label_username_date);
        timeTextView = findViewById(R.id.timesamp);
        unreadCountTextView =findViewById(R.id.unreadCount);
    }

    public void bindView(EMConversation emConversation) {
        userNameTextView.setText(emConversation.conversationId());
        if (emConversation.getLastMessage().getType() == EMMessage.Type.TXT) {
            EMTextMessageBody body = (EMTextMessageBody) emConversation.getLastMessage().getBody();
            firstLetterTextView.setText(body.getMessage());

        }else firstLetterTextView.setText(getContext().getString(R.string.messages_header));

        String timestampString = DateUtils.getTimestampString(new Date(emConversation.getLastMessage().getMsgTime()));
        timeTextView.setText(timestampString);

        if(emConversation.getUnreadMsgCount()>0){
            unreadCountTextView.setVisibility(View.VISIBLE);
            unreadCountTextView.setText(String.valueOf(emConversation.getUnreadMsgCount()));
        }else{
            unreadCountTextView.setVisibility(View.GONE);
        }
    }
}
