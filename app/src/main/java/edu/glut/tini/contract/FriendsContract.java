package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

public interface FriendsContract {

    interface Presenter extends BasePresenter {
        void loadFriends();
    }

    interface View{
        void onLoadFriendsSuccess();
        void onLoadFriendsFailed();

    }
}
