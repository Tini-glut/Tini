package edu.glut.tini.presenter;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import edu.glut.tini.contract.ChatContract;

/**
 * @author lhdigo
 * @date 2020/6/29 15:09
 */
public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;
    private List<EMMessage> messages = new ArrayList<>();
    private final int PAGE_SIZE = 10;

    public List<EMMessage> getMessages() {
        return messages;
    }

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void sendMessage(String contact, String message) {
        EMMessage emMessage = EMMessage.createTxtSendMessage(message, contact);

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                uiThread(() -> {
                    messages.add(emMessage);
                    view.onSendMessageSuccess();
                });
            }

            @Override
            public void onError(int i, String s) {
                view.onSendMessageFailed();
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
        view.onStartSendMessage();
        EMClient.getInstance().chatManager().sendMessage(emMessage);

    }

    @Override
    public void addMessage(String userName, List<EMMessage> list) {
        messages.addAll(list);
//        EMClient.getInstance().chatManager().getConversation(userName).markAllMessagesAsRead();
    }

    @Override
    public void loadMessage(String userName) {
        new Thread(() -> {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
            if (conversation != null) {
                List<EMMessage> allMessages = conversation.getAllMessages();
                conversation.markAllMessagesAsRead();
                messages.addAll(allMessages);
                conversation.setExtField("");
            }
            uiThread(() -> {
                view.onLoadedMessages();
            });


        }).start();
    }

    @Override
    public void loadMoreMessages(String userName) {

        new Thread(() -> {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
            if (conversation != null) {
                List<EMMessage> list = conversation.loadMoreMsgFromDB(messages.get(0).getMsgId(), PAGE_SIZE);
                messages.addAll(0, list);
                int size = list.size()-1;
                if (!list.isEmpty()) {
                    uiThread(() -> view.onLoadedMoreMessages(size));
                }
                 else {
                    uiThread(() -> view.onLoadedMoreMessagesFailed(size));
                }
            }else{
                uiThread(() -> view.onLoadedMoreMessagesFailed(0));
            }


        }).start();
    }
}
