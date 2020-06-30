package edu.glut.tini.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import edu.glut.tini.R;
import edu.glut.tini.adapter.EMMessageListenerAdapter;
import edu.glut.tini.adapter.MessageListAdapter;
import edu.glut.tini.contract.ChatContract;
import edu.glut.tini.presenter.ChatPresenter;

public class ChatActivity extends BaseActivity implements ChatContract.View {
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_chat;
    }

    private ImageView back;
    private TextView chatTitle;
    private EditText textEdit;
    private Button btn_send;
    private RecyclerView recyclerView;
    private ChatPresenter chatPresenter = new ChatPresenter(this);
    private String userName;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private EMMessageListener emMessageListener = new EMMessageListenerAdapter(){
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            super.onMessageReceived(list);
            chatPresenter.addMessage(userName,list);
            runOnUiThread(() -> {
                recyclerView.getAdapter().notifyDataSetChanged();
                scrollToBottom();
            });
        }
    };

    @Override
    public void init() {
        super.init();
        recyclerView = findViewById(R.id.chat_recyclerview);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        back = findViewById(R.id.chat_back);
        chatTitle = findViewById(R.id.chat_title);
        textEdit = findViewById(R.id.chat_edit);
        btn_send = findViewById(R.id.btn_send);
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        userName = getIntent().getStringExtra("userName");
        System.out.println("ChatActivity :  "+userName);
        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            recyclerView.scrollToPosition(chatPresenter.getMessages().size()-1);
        });

        //聊天界面左上角返回键监听事件
        back.setOnClickListener(v -> {
            finish();
        });
        chatTitle.setText(userName);


        initEditText(textEdit);
        btn_send.setOnClickListener(v -> {
            hideSoftKeyboard();
            chatPresenter.sendMessage(userName, textEdit.getText().toString().trim());
        });

        initRecyclerView();

    }



    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MessageListAdapter(this,chatPresenter.getMessages()));

    }


    private void initEditText(EditText textEdit) {
        btn_send = findViewById(R.id.btn_send);
        textEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_send.setEnabled(!s.toString().isEmpty());
            }
        });

        textEdit.setOnEditorActionListener((v, actionId, event) -> {
            hideSoftKeyboard();
            chatPresenter.sendMessage(userName, textEdit.getText().toString().trim());
            return true;
        });

    }

    @Override
    public void onStartSendMessage() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onSendMessageSuccess() {

        recyclerView.getAdapter().notifyDataSetChanged();
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        textEdit.getText().clear();
        System.out.println(chatPresenter.getMessages().size());
        scrollToBottom();

    }


    private void scrollToBottom() {
        recyclerView.scrollToPosition(chatPresenter.getMessages().size()-1);
    }

    @Override
    public void onSendMessageFailed() {
        Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
