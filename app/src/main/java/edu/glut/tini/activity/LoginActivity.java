package edu.glut.tini.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.glut.tini.MainActivity;
import edu.glut.tini.R;
import edu.glut.tini.contract.LoginContract;
import edu.glut.tini.presenter.LoginPresenter;

/**
 * 登录模块
 * */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    private EditText username;
    private EditText password;
    private Button loginBtn;
    private ProgressBar loaderView;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    public void init() {
        super.init();
        loginBtn = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loaderView = findViewById(R.id.loading);
        loginBtn.setOnClickListener(this::login);
    }

    private void login(View view) {
        loginPresenter.login(username.getText().toString().trim(),
                password.getText().toString().trim());
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean onUserNameError() {
        username.setError(getString(R.string.username_error));
        return true;
    }

    @Override
    public boolean onPasswordError() {
        password.setError(getString(R.string.password_error));
        return true;
    }

    @Override
    public boolean onStartLogin() {
        loaderView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onLoginSuccess() {
        loaderView.setVisibility(View.INVISIBLE);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;
    }

    @Override
    public boolean onLoginFailed() {
        loaderView.setVisibility(View.INVISIBLE);
        Toast.makeText(this,getString(R.string.login_fail),Toast.LENGTH_LONG).show();
        return true;
    }

}