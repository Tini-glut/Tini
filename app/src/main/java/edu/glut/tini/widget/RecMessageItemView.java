package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import edu.glut.tini.R;

/**
 * @author lhdigo
 * @date 2020/6/29 14:53
 */
public class RecMessageItemView extends RelativeLayout {
    public RecMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.receive_msg_item,this);
    }
}
