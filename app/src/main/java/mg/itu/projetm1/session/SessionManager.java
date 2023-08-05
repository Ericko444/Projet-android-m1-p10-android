package mg.itu.projetm1.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import mg.itu.projetm1.vues.LoginActivity;
import mg.itu.projetm1.vues.MainActivity;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";
    public static final String PROFILE = "PROFILE";
    public static final String ID = "ID";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(int id, String name, String email, String userName, String profile){
        editor.putBoolean(LOGIN, true);
        editor.putInt(ID, id);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(USERNAME, userName);
        editor.putString(PROFILE, profile);
        editor.apply();
    }

    public boolean isLogged() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public HashMap<String, Object> getUserDetail(){
        HashMap<String, Object> user = new HashMap<>();
        user.put(ID, sharedPreferences.getInt(ID, 0));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PROFILE, sharedPreferences.getString(PROFILE, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public void goToLogin() {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

}











