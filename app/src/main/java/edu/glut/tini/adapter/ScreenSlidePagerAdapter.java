package edu.glut.tini.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Ardien
 * @Date 6/26/2020 2:30 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> pages;

    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm, Map<Integer,Fragment> pages) {
        super(fm);
        assert pages != null;
        this.pages = new ArrayList<>(pages.values());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
