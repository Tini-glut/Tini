package edu.glut.tini.utils.factory;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import edu.glut.tini.R;
import edu.glut.tini.ui.fragment.AccountFragment;
import edu.glut.tini.ui.fragment.ConversationFragment;
import edu.glut.tini.ui.fragment.FriendsFragment;

/**
 * @Author Ardien
 * @Date 6/23/2020 6:41 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class FragmentFactory {
    private FragmentFactory(){}
    private static volatile Map<Integer,Fragment> instances = new HashMap<>();

    public static Fragment getInstance(int id) {
        if (!instances.containsKey(id) ) {
            synchronized (FragmentFactory.class) {
                if (!instances.containsKey(id) ) {
                    switch (id){
                        case R.id.page_account:
                            instances.put(R.id.page_account,new AccountFragment());
                            break;
                        case R.id.page_friends:
                            instances.put(R.id.page_friends,new FriendsFragment());
                            break;
                        case R.id.page_message:
                           instances.put(R.id.page_message,new ConversationFragment());
                            break;
                    }
                }
            }
        }
        return instances.get(id);
    }
}
