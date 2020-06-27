package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

public interface AddContactsContract {

    interface Presenter extends BasePresenter {
        void search(String key);
    }

    interface View{
        void onAddContactsSuccess();
        void onAddContactsFailed();
    }
}
