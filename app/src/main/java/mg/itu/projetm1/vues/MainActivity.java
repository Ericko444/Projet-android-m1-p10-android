package mg.itu.projetm1.vues;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import mg.itu.projetm1.R;
import mg.itu.projetm1.databinding.ActivityMainBinding;
import mg.itu.projetm1.session.SessionManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    private DrawerLayout drawer;
    private static final int MENU_ITEM_ACCOUNT = R.id.account;
    private static final int MENU_ITEM_HOME = R.id.home;
    private SessionManager sessionManager;
    private Fragment currentFragment;
    private boolean isDarkModeEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);
        checkPreferences();
        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        currentFragment = homeFragment;
        initToolbar();
        setNavigationColor();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.explore:
                    replaceFragment(new ExploreFragment());
                    break;
                case R.id.saved:
                    replaceFragment(new SavedFragment());
                    break;
                case R.id.account:
                    replaceFragment(new AccountFragment());
                    break;
            }
            uncheckSideNav();
            return true;
        });
        updateMenuVisibility();
    }

    private void checkPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingActivity.PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            isDarkModeEnabled = sharedPreferences.getBoolean(SettingActivity.KEY_DARK_MODE, false);
            if (isDarkModeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        drawer = findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        int color = isDarkModeEnabled ? R.color.dark_theme_text : R.color.default_text;
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(color));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void uncheckSideNav(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(false);
        }
    }

    public void uncheckBottomNav(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        menu.setGroupCheckable(0, true, false);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
        menu.setGroupCheckable(0, true, true);
    }

    private void checkBottomNavItem(int itemId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(itemId);
    }

    public void toggleToolbarVisibility(boolean show) {
        if (show) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }

    private void setNavigationColor() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        int color = isDarkModeEnabled ? R.color.dark_theme_text : R.color.default_text;
        ColorStateList tint = ColorStateList.valueOf(ContextCompat.getColor(this, color));
        navigationView.setItemTextColor(tint);
        navigationView.setItemIconTintList(tint);
        navigationView.setItemBackgroundResource(R.drawable.nav_item_background);

        Menu menu = navigationView.getMenu();
        MenuItem others= menu.findItem(R.id.nav_others);
        SpannableString s = new SpannableString(others.getTitle());
        if(isDarkModeEnabled){
            s.setSpan(new TextAppearanceSpan(this, R.style.NavigationViewTextStyle), 0, s.length(), 0);
        } else {
            s.setSpan(new TextAppearanceSpan(this, R.style.NavigationViewTextStyle), 0, s.length(), 0);
        }
        others.setTitle(s);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_account:
                replaceFragment(new AccountFragment());
                break;
            case R.id.nav_notification:
                replaceFragment(new NotifFragment());
                break;
            case R.id.nav_settings:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.nav_about_us:
                replaceFragment(new AboutUsFragment());
                break;
            case R.id.nav_login:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.nav_logout:
                invalidateOptionsMenu();
                break;
        }
        uncheckBottomNav();
        if (item.getItemId() == R.id.nav_account) {
            checkBottomNavItem(MENU_ITEM_ACCOUNT);
        }
        handleNavigationItemClick(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleNavigationItemClick(int itemId) {
        if(itemId == R.id.nav_logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm_logout, null);
            builder.setView(dialogView);

            AlertDialog alertDialog = builder.create();

            Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
            Button buttonLogout = dialogView.findViewById(R.id.buttonLogout);

            buttonCancel.setOnClickListener(v -> alertDialog.dismiss());
            buttonLogout.setOnClickListener(v -> {
                alertDialog.dismiss();
                sessionManager.logout();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            });

            alertDialog.show();
        }
    }

    private void updateMenuVisibility() {
        Menu menu = binding.navView.getMenu();

        MenuItem loginItem = menu.findItem(R.id.nav_login);
        MenuItem logoutItem = menu.findItem(R.id.nav_logout);

        if (sessionManager.isLogged()) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (!(currentFragment instanceof HomeFragment)) {
            replaceFragment(new HomeFragment());
            currentFragment = new HomeFragment();
            checkBottomNavItem(MENU_ITEM_HOME);
            return;
        }
        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        currentFragment = fragment;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateMenuVisibility();
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        super.onResume();
        uncheckSideNav();
    }
}