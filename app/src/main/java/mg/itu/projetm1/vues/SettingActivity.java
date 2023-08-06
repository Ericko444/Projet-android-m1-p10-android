package mg.itu.projetm1.vues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import mg.itu.projetm1.R;

public class SettingActivity extends AppCompatActivity {

    public static final String PREF_NAME = "my_preferences";
    public static final String KEY_DARK_MODE = "dark_mode";
    public static final String KEY_NOTIFICATIONS = "notifications";
    private SharedPreferences sharedPreferences;
    private Switch switchDarkMode;
    private Switch switchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView appVersionTextView = findViewById(R.id.app_version_text);
        appVersionTextView.setText(getAppVersion());

        getPrefs();
    }

    private void getPrefs() {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        switchDarkMode = findViewById(R.id.switch_dark_mode);
        switchNotifications = findViewById(R.id.switch_notifications);

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
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}