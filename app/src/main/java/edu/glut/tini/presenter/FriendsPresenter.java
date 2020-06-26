package edu.glut.tini.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import edu.glut.tini.contract.FriendsContract;
import edu.glut.tini.data.entity.User;
import edu.glut.tini.data.item.FriendsListItem;

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private static List<FriendsListItem> friendsListItems = new ArrayList<>();
    static {
        friendsListItems.add(new FriendsListItem("Lisi",'L'));
        friendsListItems.add(new FriendsListItem("Zhangsan",'Z'));
        friendsListItems.add(new FriendsListItem("Zhangsi",'Z'));
        friendsListItems.add(new FriendsListItem("amen",'A'));
        friendsListItems.add(new FriendsListItem("aAKJD",'A'));
        friendsListItems.add(new FriendsListItem("wangwu",'W'));
        friendsListItems.sort(new Comparator<FriendsListItem>() {
            @Override
            public int compare(FriendsListItem o1, FriendsListItem o2) {
                int diff = o1.getFirstLetter() - o2.getFirstLetter();
                if(diff>0)return 1;
                else if (diff<0)return -1;
                return 0;
            }
        });
        for (int i = 0; i < friendsListItems.size(); i++) {
            if (i==0||friendsListItems.get(i).getFirstLetter()!=friendsListItems.get(i-1).getFirstLetter()){
                friendsListItems.get(i).setShowFirstLetter(true);
            }
        }

    }
    public FriendsPresenter(FriendsContract.View view) {
        this.view = view;
    }

    public FriendsPresenter(List<FriendsListItem> friendsListItems) {
        this.friendsListItems = friendsListItems;
    }

    public List<FriendsListItem> getFriendsListItems() {
        return friendsListItems;
    }

    private List<String> userList;
    @Override
    public void loadFriends() {

        Thread t= new Thread(() -> { try {
//           userList = EMClient.getInstance().contactManager().getAllContactsFromServer();
//            System.out.println(userList);
            uiThread(() -> view.onLoadFriendsSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            uiThread(() -> view.onLoadFriendsFailed());
        }});
        t.run();

    }



}
