package edu.glut.tini.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.glut.tini.R;
import edu.glut.tini.ui.MainActivity;
import edu.glut.tini.ui.activity.LoginActivity;

/**
 *
 * */
public class AccountFragment extends BaseFragment {

    private static final String TAG = "AccountFragment";
    private TextView tvUsernameID;
    private TextView tvUsername;
    private ImageView ivAvatar;
    private Button btnLogout;
    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void init() {
        super.init();
        tvUsername = mRootView.findViewById(R.id.label_username);
        tvUsernameID = mRootView.findViewById(R.id.label_username_id);
        ivAvatar = mRootView.findViewById(R.id.image_avatar);
        btnLogout = mRootView.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this::logout);
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_account));
        initCurrentUserInfo();


    }

    /*
    * 初始化用户信息
    * */
    private void initCurrentUserInfo() {
        String username = EMClient.getInstance().getCurrentUser();
        Log.d(TAG, "init: "+username);
        tvUsername.setText(username);
        tvUsernameID.setText(username);
    }

    private void logout(View view) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                uiThread(()->{
                    context.startActivity(new Intent(getActivity(),LoginActivity.class));
                    getActivity().finish();
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }
}