package edu.glut.tini.ui.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.glut.tini.R;

public class ChatActivity extends BaseActivity {
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_chat;
    }

    private ImageView back;
    private TextView chatTitle;
    private EditText textEdit;


//    private TextView textTest;
    /*@Override
    public void init() {
        super.init();
        textTest = findViewById(R.id.textTest);
        textTest.setText(getIntent().getStringExtra("userName"));

    }*/

    @Override
    public void init() {
        super.init();
        back = findViewById(R.id.chat_back);
        chatTitle = findViewById(R.id.chat_title);
        textEdit = findViewById(R.id.chat_edit);
        System.out.println("fjadfa");
        back.setOnClickListener(v -> {
            finish();
        });
        chatTitle.setText(getIntent().getStringExtra("userName"));



    }
}
