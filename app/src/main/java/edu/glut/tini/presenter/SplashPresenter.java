package edu.glut.tini.presenter;

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

    private boolean isLoggedIn() {
        return false;
    }
}
