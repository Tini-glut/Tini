package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

public interface SplashContract {

    /**
     * 检查登录状态
     * */
    interface Presenter extends BasePresenter {
        void checkLoginStatus();
    }

    interface View {
        /**
         * 已经登录的UI处理
         * */
        void onNotLoggedIn();

        /*
        * 未登录的UI处理
        * */
        void onLoggedIn();
    }
}
