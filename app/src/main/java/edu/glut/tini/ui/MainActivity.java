package edu.glut.tini.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.glut.tini.R;
import edu.glut.tini.ui.activity.AddContactActivity;
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
        setSupportActionBar(materialToolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //获取FragmentTransaction，并且开启事务。
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.home_body,FragmentFactory.getInstance(item.getItemId()));
            beginTransaction.commit();
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_app_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                startActivity(new Intent(getApplicationContext(), AddContactActivity.class));
                return true;
            case R.id.qr_code_scanner:
                return true;
            case R.id.search:
                Toast.makeText(this,"Search",Toast.LENGTH_LONG).show();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return false;
    }


}