package edu.glut.tini.ui.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.glut.tini.R;
import edu.glut.tini.adapter.FriendsListAdapter;
import edu.glut.tini.adapter.SearchResultItemAdapter;
import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.data.item.FriendsListItem;
import edu.glut.tini.presenter.FriendsPresenter;
import edu.glut.tini.ui.activity.BaseActivity;
import edu.glut.tini.ui.fragment.FriendsFragment;

/**
 * @Author Ardien
 * @Date 6/27/2020 9:49 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class SearchableActivity extends BaseActivity {

    private RecyclerView listView;

    @Override
    public void init() {
        super.init();
        listView = findViewById(R.id.search_result);
        Bundle search = getIntent().getExtras();
        List<Contacts> searchData = (List<Contacts>) search.getSerializable("searchData");
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listView.setAdapter(new SearchResultItemAdapter(getApplicationContext(),searchData));
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_search;
    }
}
