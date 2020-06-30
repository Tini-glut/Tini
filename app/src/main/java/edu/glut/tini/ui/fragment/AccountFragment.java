package edu.glut.tini.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import edu.glut.tini.R;
import edu.glut.tini.ui.MainActivity;
import edu.glut.tini.ui.activity.AboutActivity;
import edu.glut.tini.ui.activity.LoginActivity;
import edu.glut.tini.ui.activity.SettingsActivity;
import edu.glut.tini.utils.QRCodeUtils;

/**
 *
 * */
public class AccountFragment extends BaseFragment {

    private static final String TAG = "AccountFragment";
    private TextView tvUsernameID;
    private TextView tvUsername;
    private ImageView ivAvatar;
    private View btnLogout;
    private View setting, dynamic, game, music, sports, about, read;
    private ImageView QRCode;

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
        btnLogout = mRootView.findViewById(R.id.logout);
        setting = mRootView.findViewById(R.id.settings);
        dynamic = mRootView.findViewById(R.id.dynamic);
        game = mRootView.findViewById(R.id.game);
        music = mRootView.findViewById(R.id.music);
        about = mRootView.findViewById(R.id.about);
        sports = mRootView.findViewById(R.id.sports);
        read = mRootView.findViewById(R.id.read);
        QRCode = mRootView.findViewById(R.id.icon_qr_code);

        setting.setOnClickListener(this::setting);
        btnLogout.setOnClickListener(this::logout);
        music.setOnClickListener(this::music);
        read.setOnClickListener(this::read);
        about.setOnClickListener(this::about);
        game.setOnClickListener(this::game);
        sports.setOnClickListener(this::sports);


        QRCode.setOnClickListener(this::generationQRCode);

        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_account));
        initCurrentUserInfo();

    }

    private void logout(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("注销登录")
                .setMessage("你确定要注销登录吗？")
                .setNegativeButton(context.getString(R.string.cancel),null)
                .setPositiveButton(context.getString(android.R.string.yes),(dialog, which) -> {
                    logoutFromServ();
                });
        builder.show();
    }

    private void generationQRCode(View view) {
        String username = EMClient.getInstance().getCurrentUser();
        Bitmap qrCodeBitmap = QRCodeUtils.createQRCodeBitmap(username, 300, 500);
        imgMax(qrCodeBitmap);
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

    private void logoutFromServ() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                uiThread(()->{
                    Toast.makeText(getContext(),"注销登录成功",Toast.LENGTH_LONG).show();
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

    public void imgMax(Bitmap bitmap) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View imgEntryView = inflater.inflate(R.layout.show_qr_code, null);
        // 加载自定义的布局文件
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(context).create();

        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        img.setImageBitmap(bitmap);
        // 这个是加载网络图片的，可以是自己的图片设置方法
        // imageDownloader.download(imageBmList.get(0),img);
        alertDialog.setView(imgEntryView); // 自定义dialog
        alertDialog.show();
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                alertDialog.cancel();
            }
        });
    }

}