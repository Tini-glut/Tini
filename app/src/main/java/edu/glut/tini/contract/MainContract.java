package edu.glut.tini.contract;

import java.util.List;

import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.presenter.BasePresenter;

/**
 * @Author Ardien
 * @Date 6/27/2020 9:19 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface MainContract {

    interface Presenter extends BasePresenter {
        List<Contacts> search(String key);
    }

    interface View {
        void onSearchSuccess();
        void onSearchFailed();
    }
}
