package edu.glut.tini.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import edu.glut.tini.R;
import edu.glut.tini.data.entity.ContactItem;

/**
 * @Author Ardien
 * @Date 6/27/2020 11:59 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

/*
* 带有按钮的列表显示控件项
* */
public class ActionContactShowListItem extends RelativeLayout {

    private ImageView avatar;
    private TextView username;
    private TextView date;
    private Button button;

    public ActionContactShowListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.action_contact_show_list_item,this);
        avatar = findViewById(R.id.list_image_avatar);
        username = findViewById(R.id.list_label_username);
        date = findViewById(R.id.list_label_username_date);
        button = findViewById(R.id.btn_add_contact);
        button.setOnClickListener(this::addContact);

    }

    private void addContact(View view) {
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        String toAddUsername = username.getText().toString().trim();
        String reason = "";

        new Thread(()-> {
            try {
                EMClient.getInstance().contactManager().addContact(toAddUsername, reason);
                handler.post(()->{
                    Toast.makeText(getContext(),"已发送",Toast.LENGTH_LONG).show();
                });
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void bindView(ContactItem contactItem) {
        username.setText(contactItem.getUsername());
        date.setText(contactItem.getCreateDate());

        //如果已添加为好友，则添加好友的按钮将设为不可见
        if (contactItem.isAdd()) {
            button.setVisibility(View.INVISIBLE);
        }
    }
}
