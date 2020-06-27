package edu.glut.tini.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import edu.glut.tini.R;

/**
 * @Author Ardien
 * @Date 6/27/2020 11:59 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/

/*
* 带有按钮的列表显示控件项
* */
public class ActionContactShowListItem extends RelativeLayout {
    public ActionContactShowListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.action_contact_show_list_tem,this);
    }
}
