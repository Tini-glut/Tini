package edu.glut.tini.ui.fragment;


import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.List;

import edu.glut.tini.R;
import edu.glut.tini.adapter.FriendsListAdapter;
import edu.glut.tini.contract.FriendsContract;
import edu.glut.tini.data.item.FriendsListItem;
import edu.glut.tini.presenter.FriendsPresenter;
import edu.glut.tini.ui.MainActivity;
import edu.glut.tini.widget.SlideBar;

/**
 *
 */
public class FriendsFragment extends BaseFragment implements FriendsContract.View {

    private static final int CHANNEL_ID = 3435325;
    private static FriendsPresenter friendsPresenter;
    private SlideBar slideBar;
    private TextView slideBar_letter;
    private int olderIndex = 0;
    static {
        //联系人监听器
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAdded(String s) {
                friendsPresenter.loadFriends();
            }

            @Override
            public void onContactDeleted(String s) {
                friendsPresenter.loadFriends();
            }

            @Override
            public void onContactInvited(String s, String s1) {

            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }
        });
    }


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_friends;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void init() {
        super.init();
        friendsPresenter = new FriendsPresenter(this, context);

        swipeRefreshLayout = mRootView.findViewById(R.id.swiperefreshlayout);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        slideBar = mRootView.findViewById(R.id.slideBar);
        slideBar_letter = mRootView.findViewById(R.id.letter);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);

        //下拉刷新联系人列表
        swipeRefreshLayout.setOnRefreshListener(() -> friendsPresenter.loadFriends());

        //进入联系人界面时刷新联系人列表
        friendsPresenter.loadFriendsFromDB();

        //设置标题
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_friends));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FriendsListAdapter(context, friendsPresenter.getFriendsListItems()));

        slideBar.setOnTouchLetterChangeListener(new SlideBar.OnTouchLetterChangeListener() {
            @Override
            public void onTouchLetterChange(String letter) {
                slideBar_letter.setVisibility(View.VISIBLE);
                slideBar_letter.setText(letter);
                recyclerView.smoothScrollToPosition(getPosition(letter));
            }

            @Override
            public void onSlideFinish() {
                slideBar_letter.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 获取字母在slidebar中的位置
     * @param letter 按下的字母
     * @return 字母slidebar的位置
     */
    private int getPosition(String letter) {
//        System.out.println("字母 :" + letter);
        int i = 0;
        List<FriendsListItem> friendsListItems = friendsPresenter.getFriendsListItems();
        for (; i < friendsListItems.size(); i++) {
            if (friendsListItems.get(i).getFirstLetter() == letter.charAt(0)) {
//                System.out.println("里面的i:" + i);
                olderIndex = i;
                return i;
            }
        }
//        System.out.println("olderIndex:" + olderIndex);
        return olderIndex;
    }


    /**
     * 下拉刷新好友列表成功时 成功回调函数
     * 通知适配器数据已经改变
     */
    @Override
    public void onLoadFriendsSuccess() {
        System.out.println("onLoadFriendsSuccess");
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 下拉刷新好友列表失败时 失败回调函数
     * 通知用户刷新失败
     */
    @Override
    public void onLoadFriendsFailed() {
        System.out.println("onLoadFriendsFailed");
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(context, "loadFriendsFailed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 从数据库加载好友列表 成功回调函数
     * 通知适配器数据已经改变
     */
    @Override
    public void loadFriendsFromDBSuccess() {
        System.out.println("loadFriendsFromDBSuccess");
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    private  void sendNotification(String username) {}
}