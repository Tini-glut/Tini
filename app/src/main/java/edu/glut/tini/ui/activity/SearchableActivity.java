package edu.glut.tini.ui.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import edu.glut.tini.R;
import edu.glut.tini.ui.activity.BaseActivity;

/**
 * @Author Ardien
 * @Date 6/27/2020 9:49 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class SearchableActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
