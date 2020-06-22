package edu.glut.tini.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import edu.glut.tini.contract.LoginContract;
import edu.glut.tini.contract.SplashContract;
import edu.glut.tini.utils.StringUtils;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public boolean login(String username, String password) {
        if (StringUtils.isValidUsername(username)) {
            if (StringUtils.isValidPassword(password)) {
                view.onStartLogin();
                loginEaseMod(username,password); //提交登录请求
            } else {
                view.onPasswordError();
            }
        } else {
            view.onUserNameError();
        }


        return false;
    }


    private void loginEaseMod(String username, String password) {
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
                uiThread(()->{ view.onLoginSuccess(); });
            }

            @Override
            public void onError(int i, String s) {
                uiThread(() -> view.onLoginFailed());
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
