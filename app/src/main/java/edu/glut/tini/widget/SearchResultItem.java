package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import edu.glut.tini.R;
import edu.glut.tini.data.entity.Contacts;

/**
 * @Author Ardien
 * @Date 7/8/2020 4:41 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class SearchResultItem extends RelativeLayout {
    private TextView username;
    public SearchResultItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.search_result_item,this);
        username = findViewById(R.id.search_list_label_username);
    }

    public void bindView(Contacts contacts) {
        username.setText(contacts.getContactsFriendUsername());
    }
}
