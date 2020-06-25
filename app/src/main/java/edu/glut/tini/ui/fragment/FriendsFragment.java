package edu.glut.tini.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private FriendsPresenter friendsPresenter = new FriendsPresenter(this);
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
        swipeRefreshLayout = mRootView.findViewById(R.id.swiperefreshlayout);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> friendsPresenter.loadFriends());
        MainActivity.getMaterialToolbar().setTitle(getString(R.string.text_label_friends));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FriendsListAdapter(context, friendsPresenter.getFriendsListItems()));

        friendsPresenter.loadFriends();

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