package mg.itu.projetm1.vues;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import mg.itu.projetm1.R;
import mg.itu.projetm1.session.SessionManager;

public class AccountFragment extends Fragment {
    private TextView accNameTextView;
    private TextView accUserTextView;
    private TextView accEmailTextView;
    private TextView accLogoutTextView;
    private ImageView imageView;
    private LinearLayout layout;
    private Button btnLogin;
    private ScrollView loggedInScreen;

    private SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sessionManager = new SessionManager(getActivity());

        accNameTextView = view.findViewById(R.id.acc_name);
        accUserTextView = view.findViewById(R.id.acc_user);
        accEmailTextView = view.findViewById(R.id.acc_email);
        imageView = view.findViewById(R.id.imageView);
        accLogoutTextView = view.findViewById(R.id.acc_logout);
        layout = view.findViewById(R.id.acc_logged_in);
        btnLogin = view.findViewById(R.id.acc_login_btn);
        loggedInScreen = view.findViewById(R.id.loggedInScreen);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);

        if (sessionManager.isLogged()) {
            String userName = sharedPreferences.getString(SessionManager.NAME, "");
            String userUsername = sharedPreferences.getString(SessionManager.USERNAME, "");
            String userEmail = sharedPreferences.getString(SessionManager.EMAIL, "");
            String userProfile = sharedPreferences.getString(SessionManager.PROFILE, "");

            accNameTextView.setText(userName);
            accUserTextView.setText(userUsername);
            accEmailTextView.setText(userEmail);

            if (!TextUtils.isEmpty(userProfile)) {
                Picasso.get().load(userProfile).placeholder(R.drawable.logo).into(imageView);
            }
            accLogoutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm_logout, null);
                    builder.setView(dialogView);

                    AlertDialog alertDialog = builder.create();

                    Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
                    Button buttonLogout = dialogView.findViewById(R.id.buttonLogout);

                    buttonCancel.setOnClickListener(v -> alertDialog.dismiss());
                    buttonLogout.setOnClickListener(v -> {
                        alertDialog.dismiss();
                        sessionManager.logout();
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                    });

                    alertDialog.show();
                }
            });
        } else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });

            loggedInScreen.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }

        return view;
    }
}