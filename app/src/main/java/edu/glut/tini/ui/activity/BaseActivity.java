package edu.glut.tini.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import edu.glut.tini.R;
import edu.glut.tini.utils.StatusBarUtils;


/**
 * 所有的Activity都必须继承这个类并重写getLayoutResourceId()方法。
 * */
public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager manager;
    private ProgressBar bar;

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
        if (getCurrentFocus() != null)
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    /**
     * @return 布局资源的ID，例如：R.layout.activity_main
     * */
    public abstract int getLayoutResourceId();

    /**
     * 这个方法用于初始化一些公共的功能，子类可以重写。
     * */
    public void init() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        manager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void showProgressBar(Context context) {
        bar = new ProgressBar(context);
        bar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        bar.setVisibility(View.INVISIBLE);
    }
}
