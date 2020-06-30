package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import edu.glut.tini.R;

/**
 * @author lhdigo
 * @date 2020/6/29 14:53
 */
public class RecMessageItemView extends RelativeLayout {
    public RecMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.receive_msg_item, this);
    }

    public void bindView(EMMessage emMessage, Boolean showTimeStamp) {
        updateMessage(emMessage);
        updateTimeStamp(emMessage, showTimeStamp);
    }

    private void updateMessage(EMMessage emMessage) {
        TextView receiveMsg = findViewById(R.id.receiveMsg);
        if (emMessage.getType() == EMMessage.Type.TXT) {
            EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
            receiveMsg.setText(body.getMessage());

        } else {
            receiveMsg.setText("暂不支持非文本消息");
        }
    }

    /**
     * 绑定时间
     *
     * @param emMessage
     * @param showTimeStamp
     */
    private void updateTimeStamp(EMMessage emMessage, Boolean showTimeStamp) {
        TextView receiveTime = findViewById(R.id.receiveTime);

        if (showTimeStamp) {
            receiveTime.setVisibility(VISIBLE);
            String timestampString = DateUtils.getTimestampString(new Date(emMessage.getMsgTime()));
            receiveTime.setText(timestampString);
        } else {
            receiveTime.setVisibility(GONE);
        }
    }
}
