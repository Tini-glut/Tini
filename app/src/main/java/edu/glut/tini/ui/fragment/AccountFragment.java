package edu.glut.tini.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import edu.glut.tini.R;
import edu.glut.tini.ui.MainActivity;

/**
 *
 * */
public class AccountFragment extends BaseFragment {

    private TextView usernameID;
    private TextView username;
    private ImageView ivAvatar;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void init() {
        super.init();
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_account));
    }
}