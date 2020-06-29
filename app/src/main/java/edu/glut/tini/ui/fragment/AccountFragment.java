package edu.glut.tini.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;


import edu.glut.tini.R;
import edu.glut.tini.ui.MainActivity;
import edu.glut.tini.ui.activity.AboutActivity;
import edu.glut.tini.ui.activity.LoginActivity;
import edu.glut.tini.ui.activity.SettingsActivity;

/**
 *
 * */
public class AccountFragment extends BaseFragment {

    private static final String TAG = "AccountFragment";
    private TextView tvUsernameID;
    private TextView tvUsername;
    private ImageView ivAvatar;
    private Button btnLogout;
    private View setting, dynamic, game, music, sports, about, read;

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
        setting = mRootView.findViewById(R.id.settings);
        dynamic = mRootView.findViewById(R.id.dynamic);
        game = mRootView.findViewById(R.id.game);
        music = mRootView.findViewById(R.id.music);
        about = mRootView.findViewById(R.id.about);
        sports = mRootView.findViewById(R.id.sports);
        read = mRootView.findViewById(R.id.read);

        setting.setOnClickListener(this::setting);
        btnLogout.setOnClickListener(this::logout);
        music.setOnClickListener(this::music);
        read.setOnClickListener(this::read);
        about.setOnClickListener(this::about);
        game.setOnClickListener(this::game);
        sports.setOnClickListener(this::sports);

        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_account));
        initCurrentUserInfo();

    }

    private void sports(View view) {
        Uri webpage = Uri.parse("https://www.gotokeep.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    private void game(View view) {
        Uri webpage = Uri.parse("https://yuedu.163.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    private void about(View view) {
        Intent about = new Intent(context, AboutActivity.class);
        startActivity(about);
    }

    private void read(View view) {
        Uri webpage = Uri.parse("https://yuedu.163.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);

    }



    private void music(View view) {
        Uri webpage = Uri.parse("https://music.163.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    private void setting(View view) {
        Intent setting = new Intent(context, SettingsActivity.class);
        startActivity(setting);
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