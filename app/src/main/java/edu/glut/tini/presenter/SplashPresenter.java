package edu.glut.tini.presenter;

import com.hyphenate.chat.EMClient;

import edu.glut.tini.contract.SplashContract;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void checkLoginStatus() {
        if (isLoggedIn())
            view.onLoggedIn();
        else
            view.onNotLoggedIn();
    }

    //是否登录到服务器
    private boolean isLoggedIn() {
        return EMClient.getInstance().isConnected()
                && EMClient.getInstance().isLoggedInBefore();
    }
}
