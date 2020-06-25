package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.glut.tini.R;
import edu.glut.tini.data.item.FriendsListItem;

public class FriendsListItemView extends RelativeLayout {

    private TextView firstLetterTextView;
    private TextView userNameTextView;

    public FriendsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.em_contact_item, this);
    }

    public void bindView(FriendsListItem friendsListItem) {
        firstLetterTextView = findViewById(R.id.firstLetter);
        userNameTextView = findViewById(R.id.userName);

        //首字母是否显示
        if (friendsListItem.isShowFirstLetter()) {
            firstLetterTextView.setVisibility(VISIBLE);
            firstLetterTextView.setText(String.valueOf(friendsListItem.getFirstLetter()));
        }else{
            firstLetterTextView.setVisibility(GONE);
        }
        userNameTextView.setText(friendsListItem.getUserName());

    }
}
