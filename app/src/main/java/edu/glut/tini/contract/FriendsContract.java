package edu.glut.tini.contract;

import edu.glut.tini.presenter.BasePresenter;

public interface FriendsContract {

    interface Presenter extends BasePresenter {
        void loadFriends();
        void loadFriendsFromDB();
    }

    interface View{
        void onLoadFriendsSuccess();
        void onLoadFriendsFailed();

        void loadFriendsFromDBSuccess();
    }
}
