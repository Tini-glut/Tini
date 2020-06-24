package edu.glut.tini.ui.fragment;

import edu.glut.tini.R;
import edu.glut.tini.ui.MainActivity;

/**
 *
 * */
public class FriendsFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void init() {
        super.init();
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_friends));
    }
}