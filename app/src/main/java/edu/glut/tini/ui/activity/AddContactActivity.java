package edu.glut.tini.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;

import edu.glut.tini.R;

public class AddContactActivity extends BaseActivity {
    private MaterialToolbar addContactToolBar;
    private RecyclerView searchResultRecyclerView;
    private EditText searchUsername;
    private ImageView searchBtn;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void init() {
        super.init();
        addContactToolBar = findViewById(R.id.add_contact_header_toolbar);
        addContactToolBar.setTitle(getString(R.string.text_label_add_contacts));
        searchUsername = findViewById(R.id.search_username);
        searchBtn = findViewById(R.id.btn_search_image);
        searchResultRecyclerView = findViewById(R.id.add_contact_search_result);
    }
}