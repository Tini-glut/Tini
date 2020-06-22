package edu.glut.tini.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 所有的Activity都必须继承这个类并重写getLayoutResourceId()方法。
 * */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        init();
    }

    /**
     * @return 布局资源的ID，例如：R.layout.activity_main
     * */
    public abstract int getLayoutResourceId();

    /**
     * 这个方法用于初始化一些公共的功能，子类可以重写。
     * */
    public void init() {

    }
}
