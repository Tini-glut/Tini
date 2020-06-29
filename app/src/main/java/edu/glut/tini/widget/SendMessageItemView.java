package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import org.w3c.dom.Text;

import java.util.Date;

import edu.glut.tini.R;

/**
 * @author lhdigo
 * @date 2020/6/29 14:43
 */
public class SendMessageItemView extends RelativeLayout {
    public SendMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.send_msg_item, this);
    }

    public void bindView(EMMessage emMessage) {
        updateTimeStamp(emMessage);
        updateMessage(emMessage);
    }

    private void updateMessage(EMMessage emMessage) {
        TextView sendMsg= findViewById(R.id.sendMsg);
        if (emMessage.getType() == EMMessage.Type.TXT){
            EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
            sendMsg.setText(body.getMessage());

        }else {
            sendMsg.setText("暂不支持非文本消息");

        }
    }
    /**
     * 绑定时间
     *
     * @param emMessage
     */
    private void updateTimeStamp(EMMessage emMessage) {
        TextView sendTime = findViewById(R.id.sendTime);
        String timestampString = DateUtils.getTimestampString(new Date(emMessage.getMsgTime()));
        sendTime.setText(timestampString);
    }
}
