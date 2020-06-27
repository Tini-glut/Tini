package edu.glut.tini.presenter;

import android.content.Context;

import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

import java.util.List;

import edu.glut.tini.contract.FriendsContract;
import edu.glut.tini.data.AppDatabase;
import edu.glut.tini.data.dao.ContactsDao;
import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.data.item.FriendsListItem;

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private List<FriendsListItem> friendsListItems = new ArrayList<>();
    private Context context;

    public FriendsPresenter(FriendsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public List<FriendsListItem> getFriendsListItems() {
        return friendsListItems;
    }

    private List<String> userList;


    /**
     * 从环信服务器获取好友信息
     */
    @Override
    public void loadFriends() {
        Thread t = new Thread(() -> {
            try {
                friendsListItems.clear();
                userList = EMClient.getInstance().contactManager().getAllContactsFromServer();
                for (String s : userList) {
                    friendsListItems.add(new FriendsListItem(s, s.toUpperCase().charAt(0), false));
                }
                sortFriends(friendsListItems);
                saveFriendsIntoDb(friendsListItems, context);
                System.out.println(userList);
                System.out.println(friendsListItems);
                uiThread(() -> view.onLoadFriendsSuccess());
            } catch (Exception e) {
                e.printStackTrace();
                uiThread(() -> view.onLoadFriendsFailed());
            }
        });
        t.start();

    }

    /**
     * 从本地数据库获取好友信息
     */
    @Override
    public void loadFriendsFromDB() {
        Thread thread = new Thread(() -> {
            ContactsDao contactsDao = AppDatabase.getInstance(context).getContactsDao();
            List<Contacts> contacts = contactsDao.selectAll();

            if (!contacts.isEmpty()) {
                for (Contacts contact : contacts) {
                    friendsListItems.add(new FriendsListItem(contact.getContactsFriendUsername(),
                            contact.getContactsFriendUsername().toUpperCase().charAt(0),
                            false));
                }
                sortFriends(friendsListItems);
                uiThread(() -> view.loadFriendsFromDBSuccess());
            } else {
                uiThread(() -> view.loadFriendsFromDBFailed());
            }

        });
        thread.start();
    }

    /**
     * 按首字母排序 并且 根据确定是否显示首字母
     *
     * @param friendsListItems
     */
    void sortFriends(List<FriendsListItem> friendsListItems) {
        friendsListItems.sort((o1, o2) -> {
            int diff = o1.getFirstLetter() - o2.getFirstLetter();
            if (diff > 0) return 1;
            else if (diff < 0) return -1;
            return 0;
        });

        for (int i = 0; i < friendsListItems.size(); i++) {
            //1.若是第一个 显示      2.若前一个首字母和当前首字母不同 显示
            if (i == 0 || friendsListItems.get(i).getFirstLetter() != friendsListItems.get(i - 1).getFirstLetter()) {
                friendsListItems.get(i).setShowFirstLetter(true);
            }
        }
    }

    void saveFriendsIntoDb(List<FriendsListItem> friendsListItems, Context context) {

        ContactsDao contactsDao = AppDatabase.getInstance(context).getContactsDao();
        contactsDao.deleteAll();
//      contactsDao.updateContactsSeq();
        for (int i = 0; i < friendsListItems.size(); i++) {
            String userName = friendsListItems.get(i).getUserName();
            contactsDao.insertInto(userName);
        }
    }


}
