package mg.itu.projetm1.vues;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import mg.itu.projetm1.R;
import mg.itu.projetm1.controllers.Controller;
import mg.itu.projetm1.session.SessionManager;

import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn;
    private TextView sign_up;
    private ProgressBar loading;

    private Controller controller;
    SessionManager sessionManager;
    private static String URL_LOGIN = "api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        this.controller = Controller.getInstance();
        sessionManager = new SessionManager(this);
    }

    private void init(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        sign_up = findViewById(R.id.sign_up);
        loading = findViewById(R.id.loading);
        loginListener();
        signUpListener();
    }

    private void loginListener(){
        if(loginBtn != null){
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailTxt = email.getText().toString().trim();
                    String pwdTxt = password.getText().toString().trim();

                    if(!emailTxt.isEmpty() || !pwdTxt.isEmpty()){
                        login(emailTxt, pwdTxt);
                    } else {
                        Toast.makeText(LoginActivity.this, "Veillez remplir les champs", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void signUpListener(){
        if(sign_up != null){
            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    finish();
                }
            });
        }
    }

    private void login(String emailTxt, String pwdTxt){
        loading.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);

        JSONObject requestObject = new JSONObject();
        try {
            String registrationToken = SplashScreen.getToken();
            Log.d("FCM TOKEN", registrationToken);
            requestObject.put("email", emailTxt);
            requestObject.put("password", pwdTxt);
            requestObject.put("token", registrationToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                controller.getBASE_URL()+URL_LOGIN,
                requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONObject userData = response.getJSONObject("data");
                                loading.setVisibility(View.GONE);
                                sessionManager.createSession(userData.getInt("id"), userData.getString("name"), userData.getString("email"), userData.getString("username"), userData.getString("profile"));
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                String message = response.getString("message");
                                loading.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        loginBtn.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                        String errorMessage = "An error occurred";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String errorData = new String(error.networkResponse.data, "UTF-8");
                                JSONObject errorJson = new JSONObject(errorData);
                                if (errorJson.has("message")) {
                                    errorMessage = errorJson.getString("message");
                                }
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                                Log.d("LoginActivity", "UnsupportedEncodingException: " + e.getMessage());
                            }
                        }
                        // Display the error message
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("LoginActivity", "onErrorResponse: " + errorMessage);
                    }
                }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public String getInstanceToken(){
        final String[] instanceToken = {""};
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        instanceToken[0] = token;
                    }
                });

        return instanceToken[0];
    }
}