package edu.glut.tini.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Objects;

import edu.glut.tini.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private ListPreference darkModeSelect;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SwitchPreferenceCompat darkMode = findPreference(getString(R.string.dark_mode_key));
        darkModeSelect = findPreference(getString(R.string.dark_mode_select_key));
        assert darkModeSelect != null;
        assert darkMode != null;

        darkModeSelect.setOnPreferenceChangeListener(this::onDarkModeSelectPreferenceChange);

        darkMode.setOnPreferenceChangeListener(this::onDarkModeSelectPreferenceChange);
    }


    private boolean onDarkModeSelectPreferenceChange(Preference preference, Object newValue) {

        return true;
    }

    private boolean onDarkModePreferenceChange(Preference preference, Object newValue) {

        return true;
    }
}