package edu.glut.tini.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
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

    public List<EMMessage> getMessages() {
        return messages;
    }

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void sendMessage(String contact, String message) {
        EMMessage emMessage = EMMessage.createTxtSendMessage(message,contact);

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
}
