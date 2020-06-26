package edu.glut.tini.ui.fragment;


import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import edu.glut.tini.R;
import edu.glut.tini.adapter.FriendsListAdapter;
import edu.glut.tini.contract.FriendsContract;
import edu.glut.tini.data.item.FriendsListItem;
import edu.glut.tini.presenter.FriendsPresenter;
import edu.glut.tini.ui.MainActivity;

/**
 *
 */
public class FriendsFragment extends BaseFragment implements FriendsContract.View {

//    private FriendsPresenter friendsPresenter = new FriendsPresenter(this,context);
    private FriendsPresenter friendsPresenter ;
    private FriendsListItem friendsListItem;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_friends;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void init() {
        super.init();
        friendsPresenter = new FriendsPresenter(this,context);
        swipeRefreshLayout = mRootView.findViewById(R.id.swiperefreshlayout);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
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

        //联系人监听器
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {

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
    public void onLoadFriendsSuccess() {
        System.out.println("onLoadFriendsSuccess");
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadFriendsFailed() {
        System.out.println("onLoadFriendsFailed");
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(context, "loadFriendsFailed", Toast.LENGTH_SHORT).show();
    }
}