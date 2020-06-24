package edu.glut.tini.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.glut.tini.R;
import edu.glut.tini.contract.RegisterContract;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private EditText tvUsername,tvPassword,tvConfirmPassword;
    private Button btnRegister;
    private ProgressBar loading;

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
        tvConfirmPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void onStartRegister() {
        loading.setVisibility(View.VISIBLE);
        finish();
    }

    @Override
    public void onRegisterSuccess() {
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRegisterFailed() {
        loading.setVisibility(View.INVISIBLE);
        Toast.makeText(this,getString(R.string.register_failed),Toast.LENGTH_LONG).show();
    }
}