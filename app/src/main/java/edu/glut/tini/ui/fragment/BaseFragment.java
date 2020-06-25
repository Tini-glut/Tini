package edu.glut.tini.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.glut.tini.presenter.BasePresenter;
import edu.glut.tini.ui.MainActivity;
import edu.glut.tini.ui.activity.BaseActivity;

/**
 * @Author Ardien
 * @Date 6/23/2020 5:08 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public abstract class BaseFragment extends Fragment implements BasePresenter {

    protected View mRootView;
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResourceId(), container, false);
        init();
        return mRootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =  context;
    }

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {   }

    protected abstract int setLayoutResourceId();
}
