package edu.glut.tini.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import edu.glut.tini.R;
import edu.glut.tini.adapter.AddContactAdapter;
import edu.glut.tini.contract.AddContactsContract;
import edu.glut.tini.presenter.AddContactsPresenter;

public class AddContactActivity extends BaseActivity implements AddContactsContract.View {
    private MaterialToolbar addContactToolBar;
    private RecyclerView searchResultRecyclerView;
    private EditText searchKey;
    private ImageView searchBtn;
    private AddContactsPresenter contactsPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void init() {
        super.init();
        addContactToolBar = findViewById(R.id.add_contact_header_toolbar);
        addContactToolBar.setTitle(getString(R.string.text_label_add_contacts));
        searchKey = findViewById(R.id.search_username);
        searchBtn = findViewById(R.id.btn_search_image);
        searchResultRecyclerView = findViewById(R.id.add_contact_search_result);
        contactsPresenter = new AddContactsPresenter(this,getApplicationContext());

        searchResultRecyclerView.setHasFixedSize(true);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchResultRecyclerView.setAdapter(new AddContactAdapter(getApplicationContext(),contactsPresenter.getData()));
        searchBtn.setOnClickListener(this::startSearch);
        searchKey.setOnEditorActionListener(this::startSearch);
    }

    private boolean startSearch(TextView textView, int i, KeyEvent keyEvent) {
        startSearch(textView);
        return true;
    }

    private void startSearch(View view) {
        String key = searchKey.getText().toString().trim();
        if (key.isEmpty()) return;
        contactsPresenter.search(key);
        hideSoftKeyboard();
    }

    @Override
    public void onAddContactsSuccess() {
        searchResultRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAddContactsFailed() {
    }
}