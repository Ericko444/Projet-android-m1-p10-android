package mg.itu.projetm1.vues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import mg.itu.projetm1.R;
import mg.itu.projetm1.session.SessionManager;

public class SettingFragment extends Fragment {

    private static final String PREF_NAME = "my_preferences";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private SharedPreferences sharedPreferences;
    private Switch switchDarkMode;
    private Switch switchNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.toggleToolbarVisibility(false); // Hide the toolbar
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.toggleToolbarVisibility(true); // Show the toolbar
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView appVersionTextView = view.findViewById(R.id.app_version_text);
        appVersionTextView.setText(getAppVersion());
        getPrefs(view);
    }

    private void getPrefs(View view) {
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        switchNotifications = view.findViewById(R.id.switch_notifications);

        boolean isDarkModeEnabled = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        boolean areNotificationsEnabled = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, true);

        switchDarkMode.setChecked(isDarkModeEnabled);
        switchNotifications.setChecked(areNotificationsEnabled);

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_DARK_MODE, isChecked);
                editor.apply();

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_NOTIFICATIONS, isChecked);
                editor.apply();
            }
        });
    }

    private String getAppVersion() {
        String version = "";
        try {
            PackageInfo packageInfo = requireActivity().getPackageManager().getPackageInfo(requireActivity().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}