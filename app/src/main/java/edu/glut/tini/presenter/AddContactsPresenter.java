package edu.glut.tini.presenter;

import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import edu.glut.tini.contract.AddContactsContract;
import edu.glut.tini.data.entity.ContactItem;
import edu.glut.tini.data.entity.User;

/**
 * @Author Ardien
 * @Date 6/27/2020 1:30 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class AddContactsPresenter implements AddContactsContract.Presenter {

    private AddContactsContract.View view;
    private List<ContactItem> data = new ArrayList<>();

    public List<ContactItem> getData() {
        return data;
    }

    public AddContactsPresenter(AddContactsContract.View view) {
        this.view = view;
    }

    @Override
    public void search(String key) {
        BmobQuery<User> search = new BmobQuery<>();
        search.addWhereContains("username",key)
              .addWhereNotEqualTo("username",EMClient.getInstance().getCurrentUser());
        search.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    list.forEach(user -> {
                        ContactItem item =
                                new ContactItem(user.getUsername(),user.getCreatedAt(),false);
                        data.add(item);
                    });
                    uiThread(()-> view.onAddContactsSuccess());
                }
            }
        });
    }
}
