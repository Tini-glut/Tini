package edu.glut.tini.presenter;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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


                    //注册到本地服务器
                    registerOnBmodServer(username,password);

                } else view.onConfirmPasswordError();
            } else view.onPasswordError();
        }else view.onUserNameError();
        return true;
    }

    private void registerOnBmodServer(String username, String password) {
        User user = new User(username,password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    registerOnIMServer(username,password);
                }else {
                    view.onRegisterFailed();
                }
            }
        });

    }


    private void registerOnIMServer(String username, String password) {
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
