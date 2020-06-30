package edu.glut.tini.ui.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class ChatActivity extends BaseActivity implements ChatContract.View {
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_chat;
    }

    private ImageView chat_back;
    private TextView chatTitle;
    private EditText textEdit;
    private Button btn_send;
    private RecyclerView recyclerView;
    private ChatPresenter chatPresenter = new ChatPresenter(this);
    private String userName;

    private Context context;
    private EMMessageListener emMessageListener = new EMMessageListenerAdapter() {

        //收到消息
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            super.onMessageReceived(list);
            chatPresenter.addMessage(userName, list);
            runOnUiThread(() -> {
                recyclerView.getAdapter().notifyDataSetChanged();
                //滚动到底部
                scrollToBottom();
            });
        }
    };

    @Override
    public void init() {
        super.init();
        recyclerView = findViewById(R.id.chat_recyclerview);
        chat_back = findViewById(R.id.chat_back);
        chatTitle = findViewById(R.id.chat_title);
        textEdit = findViewById(R.id.chat_edit);


        textEdit.setImeOptions(EditorInfo.IME_ACTION_SEND);

        textEdit.setInputType(TYPE_TEXT_FLAG_MULTI_LINE);

        textEdit.setSingleLine(false);

        textEdit.setMaxLines(4);


        btn_send = findViewById(R.id.btn_send);
        context = this;

        //聊天信息监听
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

        //获取聊天对象用户名
        userName = getIntent().getStringExtra("userName");

        //聊天界面左上角返回键监听事件
        chat_back.setOnClickListener(v -> {
            finish();
        });

        //聊天界面header标题    聊天对象用户名
        chatTitle.setText(userName);

        //初始化输入框  包括监听发送按钮
        initEditText(textEdit);
        //初始化聊天界面 包括设置适配器
        initRecyclerView();

        //点击发送按钮的监听事件
        btn_send.setOnClickListener(v -> {
            String message = textEdit.getText().toString();
            if (message.isEmpty()) return;
            hideSoftKeyboard();
            chatPresenter.sendMessage(userName, textEdit.getText().toString());
        });

        //初始化聊天记录的加载
        chatPresenter.loadMessage(userName);
    }


    /**
     * 初始化 RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //打开软键盘时 recyclerview滚动到底部
        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            recyclerView.scrollToPosition(chatPresenter.getMessages().size() - 1);
        });
        recyclerView.setAdapter(new MessageListAdapter(this, chatPresenter.getMessages()));

        //下拉加载更多聊天记录
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
                Log.d("addOnScrollListener", "ahahhhah");
                Log.d("newState   ", String.valueOf(newState));
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //  如果第一个可见条目位置等于0  则现在是最顶部
                    Log.d("newState == RecyclerView.SCROLL_STATE_IDLE","newState == RecyclerView.SCROLL_STATE_IDLE");
                    Log.d("onScrollStateChanged", "onScrollStateChanged: "+
                            String.valueOf(linearLayoutManager1.findFirstVisibleItemPosition()));
                    if (linearLayoutManager1.findFirstVisibleItemPosition() == -1) {
                        Log.d("linearLayoutManager1.findFirstVisibleItemPosition() == 0",
                                "linearLayoutManager1.findFirstVisibleItemPosition() == 0");
                        chatPresenter.loadMoreMessages(userName);
                    }
                }
            }
        });
    }

    /**
     * 初始化输入框
     *
     * @param textEdit
     */
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

    /**
     * 聊天界面  滚动到屏幕底部
     */
    private void scrollToBottom() {
        recyclerView.scrollToPosition(chatPresenter.getMessages().size() - 1);
    }

    @Override
    public void onSendMessageFailed() {
        Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
        recyclerView.getAdapter().notifyDataSetChanged();

    }


    @Override
    public void onLoadedMessages() {
        recyclerView.getAdapter().notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void onLoadedMoreMessages(int size) {
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(size);
    }
}
