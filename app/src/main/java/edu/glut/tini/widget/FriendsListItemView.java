package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.glut.tini.R;
import edu.glut.tini.data.item.FriendsListItem;

public class FriendsListItemView extends RelativeLayout {

    private TextView firstLetterTextView;
    private TextView userNameTextView;
    private ImageView friendsAvatar;
    private TextView friendsCount;

    public FriendsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.em_contact_item, this);
        firstLetterTextView = findViewById(R.id.firstLetter);
        userNameTextView = findViewById(R.id.userName);
        friendsAvatar = findViewById(R.id.friends_avatar);
        friendsCount = findViewById(R.id.friends_count);
    }

    public void bindView(FriendsListItem friendsListItem) {
        //首字母是否显示
        if (friendsListItem.isShowFirstLetter()) {
            firstLetterTextView.setVisibility(VISIBLE);
            firstLetterTextView.setText(String.valueOf(friendsListItem.getFirstLetter()));
        } else {
            firstLetterTextView.setVisibility(GONE);
        }

        userNameTextView.setVisibility(VISIBLE);
        friendsAvatar.setVisibility(VISIBLE);
        friendsCount.setVisibility(INVISIBLE);

        userNameTextView.setText(friendsListItem.getUserName());

    }

    public void bindViewOfFriendsCount(int itemCount) {
        firstLetterTextView.setVisibility(GONE);
        userNameTextView.setVisibility(INVISIBLE);
        friendsAvatar.setVisibility(INVISIBLE);
        friendsCount.setVisibility(VISIBLE);

        friendsCount.setText("共有"+itemCount+"位联系人");
    }
}
