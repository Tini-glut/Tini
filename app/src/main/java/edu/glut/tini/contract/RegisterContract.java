package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

/**
 * @Author Ardien
 * @Date 6/24/2020 2:51 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface RegisterContract {

    interface Presenter extends BasePresenter {
        boolean register(String username, String password, String confirmPassword);
    }

    interface View {
        void onUserNameError();
        void onPasswordError();
        void onConfirmPasswordError();
        void onStartRegister();
        void onRegisterSuccess();
        void onRegisterFailed();
    }
}
