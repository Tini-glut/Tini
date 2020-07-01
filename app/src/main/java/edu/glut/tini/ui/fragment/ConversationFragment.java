package edu.glut.tini.ui.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.glut.tini.R;
import edu.glut.tini.adapter.ConversationListAdapter;

import edu.glut.tini.adapter.EMMessageListenerAdapter;
import edu.glut.tini.ui.MainActivity;

/**
 *
 */
public class ConversationFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_conversation;
    }

    private RecyclerView recyclerView;
    private List<EMConversation> conversations = new ArrayList<>();

    private EMMessageListenerAdapter adapter;

    @Override
    protected void init() {
        super.init();

        adapter =new EMMessageListenerAdapter(){
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                loadConversations();
            }
        };
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_conversation));
        recyclerView = mRootView.findViewById(R.id.conversation_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ConversationListAdapter(context,conversations));

        //监听器
        EMClient.getInstance().chatManager().addMessageListener(adapter);

//        loadConversations();

    }

    private void loadConversations() {

        conversations.clear();
        new Thread(() -> {
            Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
            conversations.addAll((allConversations.values()));
           uiThread(() -> {
               recyclerView.getAdapter().notifyDataSetChanged();
           });
        }).start();

    }

    @Override
    public void onResume() {
        super.onResume();
        conversations.clear();
        loadConversations();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(adapter);
    }
}