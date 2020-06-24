package edu.glut.tini.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import edu.glut.tini.contract.LoginContract;
import edu.glut.tini.contract.RegisterContract;
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
                } else view.onPasswordError();
            } else view.onPasswordError();
        }else view.onUserNameError();


        return false;
    }
}
