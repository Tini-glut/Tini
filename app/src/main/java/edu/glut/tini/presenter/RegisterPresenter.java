package edu.glut.tini.presenter;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.glut.tini.contract.RegisterContract;
import edu.glut.tini.data.AppDatabase;
import edu.glut.tini.data.dao.UserDao;
import edu.glut.tini.data.entity.User;
import edu.glut.tini.utils.StringUtils;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public boolean register(String username, String password, String confirmPassword) {
        //校验用户名
        if (StringUtils.isValidUsername(username)) {
            //校验密码
            if (StringUtils.isValidPassword(password)){
                // 校验确认密码
                if (password.equals(confirmPassword)) {
                    view.onStartRegister();
                    //注册到环信服务器
                    registerOnServer(username,password);
                } else view.onConfirmPasswordError();
            } else view.onPasswordError();
        }else view.onUserNameError();
        return true;
    }


    private void registerOnServer(String username, String password) {
        new Thread(()->{
            try {
                EMClient.getInstance().createAccount(username, password);
                view.onRegisterSuccess();
            } catch (HyphenateException e) {
                e.printStackTrace();
                view.onRegisterFailed();
            }
        }).start();
    }
}
