package edu.glut.tini.contract;

import com.hyphenate.chat.EMMessage;

import java.util.List;

import edu.glut.tini.presenter.BasePresenter;

/**
 * @author lhdigo
 * @date 2020/6/29 14:57
 */
public interface ChatContract {
    interface Presenter extends BasePresenter{

        void sendMessage(String contact,String message);

        void addMessage(String userName, List<EMMessage> list);

        void loadMessage(String userName);

        void loadMoreMessages(String userName);
    }

    interface View{
        void onStartSendMessage();
        void onSendMessageSuccess();
        void onSendMessageFailed();

        void onLoadedMessages();

        void onLoadedMoreMessages(int size);
    }
}
