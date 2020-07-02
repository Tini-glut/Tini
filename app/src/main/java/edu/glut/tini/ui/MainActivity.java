package edu.glut.tini.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import edu.glut.tini.R;
import edu.glut.tini.adapter.EMMessageListenerAdapter;
import edu.glut.tini.contract.MainContract;
import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.presenter.MainPresenter;
import edu.glut.tini.ui.activity.AddContactActivity;
import edu.glut.tini.ui.activity.BaseActivity;
import edu.glut.tini.ui.activity.ColorLensActivity;
import edu.glut.tini.ui.activity.SearchableActivity;
import edu.glut.tini.utils.factory.FragmentFactory;

public class MainActivity extends BaseActivity implements MainContract.View {
    private BottomNavigationView bottomNavigationView;
    private static MaterialToolbar materialToolbar;
    private SearchView searchView;
    private MainContract.Presenter mainPresenter;
    private TextView count;
    public TextView getCount() {
        return count;
    }

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
        //未读消息角标
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(0);
        View badge = LayoutInflater.from(this).inflate(R.layout.count, menuView, false);
        itemView.addView(badge);
        count = findViewById(R.id.message_count);



        AtomicReference<FragmentTransaction> beginTransaction = new AtomicReference<>(getSupportFragmentManager().beginTransaction());
        beginTransaction.get().replace(R.id.home_body,FragmentFactory.getInstance(R.id.page_message)).commit();
        mainPresenter = new MainPresenter(this,getApplicationContext());
        materialToolbar.setTitle(getString(R.string.text_label_conversation));
        setSupportActionBar(materialToolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //获取FragmentTransaction，并且开启事务。
            int messageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
            if (messageCount > 0 && item.getItemId() != R.id.page_message) {
                count.setText(String.valueOf(messageCount));
                count.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.page_message) count.setVisibility(View.INVISIBLE);
            beginTransaction.set(getSupportFragmentManager().beginTransaction());
            beginTransaction.get().replace(R.id.home_body,FragmentFactory.getInstance(item.getItemId()));
            beginTransaction.get().commit();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_app_bar,menu);
        MenuItem searchItem =  menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint(getString(R.string.query_hint_label));
        //获取焦点
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Contacts> data  = mainPresenter.search(query);
                if (data.size() == 0) {
                    runOnUiThread(()->{
                        Toast.makeText(getApplicationContext(),"没有联系人哦！",Toast.LENGTH_LONG).show();
                    });
                    return true;
                }
                Intent search = new Intent(getApplicationContext(),SearchableActivity.class);
                search.putExtra("searchData", (Serializable) data);
                startActivity(search);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //List<Contacts> data  = mainPresenter.search(newText);
                //if (TextUtils.isEmpty(newText)) searchView.setVisibility(View.INVISIBLE);
                //Log.d(this.toString(), "onQueryTextChange: "+ data.toString());
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contact:
                startActivity(new Intent(getApplicationContext(), AddContactActivity.class));
                return true;
            case R.id.qr_code_scanner:
                scanQRCode();
                return true;
            case R.id.color_lens:
                buildColorLensAlertDialog(getApplicationContext());
            default:
                super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void buildColorLensAlertDialog(Context context) {
        startActivity(new Intent(this, ColorLensActivity.class));
    }


    @Override
    public void onSearchSuccess() {

    }

    @Override
    public void onSearchFailed() {

    }

    private void scanQRCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                Handler handler = new android.os.Handler(Looper.getMainLooper());
                String toAddUsername = result.getContents();
                //Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                EMClient.getInstance()
                        .contactManager()
                        .aysncAddContact(toAddUsername, null, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                handler.post(()->{
                                    Toast.makeText(getApplicationContext(),"已发送好友请求给"+toAddUsername,Toast.LENGTH_LONG).show();
                                });
                            }

                            @Override
                            public void onError(int i, String s) {
                                handler.post(()->{
                                    Toast.makeText(getApplicationContext(),"发送好友请求失败",Toast.LENGTH_LONG).show();
                                });
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}