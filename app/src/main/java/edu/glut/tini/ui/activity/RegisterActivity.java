package edu.glut.tini.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.glut.tini.R;
import edu.glut.tini.contract.RegisterContract;
import edu.glut.tini.data.dao.UserDao;
import edu.glut.tini.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private EditText tvUsername,tvPassword,tvConfirmPassword;
    private Button btnRegister;
    private ProgressBar loading;
    private RegisterPresenter registerPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        super.init();
        tvUsername = findViewById(R.id.tv_register_username);
        tvPassword = findViewById(R.id.tv_register_password);
        tvConfirmPassword = findViewById(R.id.tv_register_repassword);
        btnRegister = findViewById(R.id.btn_register);
        loading = findViewById(R.id.loading_register);
        registerPresenter = new RegisterPresenter(this);
        tvConfirmPassword.setOnEditorActionListener(this::onClickListener);
        btnRegister.setOnClickListener(this::register);
    }

    private boolean onClickListener(TextView textView, int i, KeyEvent keyEvent) {
        register(textView);
        return true;
    }


    private void register(View view) {
        //hideSoftKeyboard();
        String username = tvUsername.getText().toString().trim();
        String password = tvPassword.getText().toString().trim();
        String password2 = tvConfirmPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) return;
        registerPresenter.register(username, password, password2);
    }

    @Override
    public void onUserNameError() {
        tvUsername.setError(getString(R.string.username_error));
    }

    @Override
    public void onPasswordError() {
        tvPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void onConfirmPasswordError() {
        tvConfirmPassword.setError(getString(R.string.password_error_not_equals));
    }

    @Override
    public void onStartRegister() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRegisterSuccess() {
        Looper.prepare();
        loading.setVisibility(View.INVISIBLE);
        Toast.makeText(this,getString(R.string.register_success),Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }

    @Override
    public void onRegisterFailed() {
        loading.setVisibility(View.INVISIBLE);
        Toast.makeText(this,getString(R.string.register_failed),Toast.LENGTH_LONG).show();
    }
}