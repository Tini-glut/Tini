package edu.glut.tini.ui;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.glut.tini.R;
import edu.glut.tini.ui.activity.BaseActivity;
import edu.glut.tini.utils.factory.FragmentFactory;

public class MainActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        bottomNavigationView = findViewById(R.id.tab_bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //获取FragmentTransaction，并且开启事务。
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.home_body,FragmentFactory.getInstance(item.getItemId()));
            beginTransaction.commit();
            return true;
        });
    }
}