package edu.glut.tini.ui;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.glut.tini.R;
import edu.glut.tini.ui.activity.BaseActivity;
import edu.glut.tini.utils.factory.FragmentFactory;

public class MainActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private static MaterialToolbar materialToolbar;

    public static MaterialToolbar getMaterialToolbar() {
        return materialToolbar;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        materialToolbar = findViewById(R.id.header_toolbar);
        bottomNavigationView = findViewById(R.id.tab_bottom_bar);
        bottomNavigationView.setSelectedItemId(R.id.page_message);
        materialToolbar.setTitle(getString(R.string.text_label_conversation));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //获取FragmentTransaction，并且开启事务。
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.home_body,FragmentFactory.getInstance(item.getItemId()));
            beginTransaction.commit();
            return true;
        });
    }
}