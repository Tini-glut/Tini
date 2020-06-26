package edu.glut.tini.ui.activity;

import android.content.Intent;
import android.widget.TextView;

import edu.glut.tini.R;

public class ChatActivity extends BaseActivity {
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_chat;
    }


    private TextView textTest;
    @Override
    public void init() {
        super.init();
        textTest = findViewById(R.id.textTest);
        textTest.setText(getIntent().getStringExtra("userName"));

    }
}
