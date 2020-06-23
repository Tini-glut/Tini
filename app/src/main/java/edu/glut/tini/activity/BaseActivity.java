package edu.glut.tini.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


/**
 * 所有的Activity都必须继承这个类并重写getLayoutResourceId()方法。
 * */
public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        init();
    }

    /**
     * bug:登录失败时输入法不会自动收回
     * fix：隐藏软键盘
     * */
    public void hideSoftKeyboard() {
        manager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),0);
    }

    /**
     * @return 布局资源的ID，例如：R.layout.activity_main
     * */
    public abstract int getLayoutResourceId();

    /**
     * 这个方法用于初始化一些公共的功能，子类可以重写。
     * */
    public void init() {
        manager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
