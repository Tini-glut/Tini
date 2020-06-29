package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

/**
 * @author lhdigo
 * @date 2020/6/29 14:57
 */
public interface ChatContract {
    interface Presenter extends BasePresenter{

        void sendMessage(String contact,String message);
    }

    interface View{
        void onStartSendMessage();
        void onSendMessageSuccess();
        void onSendMessageFailed();
    }
}
