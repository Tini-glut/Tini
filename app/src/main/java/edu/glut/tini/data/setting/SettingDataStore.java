package edu.glut.tini.data.setting;

import androidx.preference.PreferenceDataStore;

/**
 * @Author Ardien
 * @Date 7/1/2020 8:57 AM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class SettingDataStore extends PreferenceDataStore {

    @Override
    public void putBoolean(String key, boolean value) {
        super.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return super.getBoolean(key, defValue);
    }
}
