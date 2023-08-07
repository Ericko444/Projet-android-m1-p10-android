package mg.itu.projetm1.vues;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import mg.itu.projetm1.R;
import mg.itu.projetm1.controllers.Controller;
import mg.itu.projetm1.session.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, first_name, email, password, password_confirm;
    private Button signUpBtn;
    private TextView sign_in;
    private ProgressBar loading;
    private Controller controller;
    SessionManager sessionManager;

    private static String URL_REGISTER = "api/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        this.controller = Controller.getInstance();
        sessionManager = new SessionManager(this);
    }

    private void init(){
        name = findViewById(R.id.name);
        first_name = findViewById(R.id.first_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);
        signUpBtn = findViewById(R.id.signUpBtn);
        sign_in = findViewById(R.id.sign_in);
        loading = findViewById(R.id.loading);
        signUpListener();
        login();
    }

    private void login(){
        if(sign_in != null){
            sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            });
        }
    }

    private void signUpListener(){
        if(signUpBtn != null){
            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signUp();
                }
            });
        }
    }

    private void signUp(){
        loading.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String first_name = this.first_name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("name", name);
            requestObject.put("first_name", first_name);
            requestObject.put("email", email);
            requestObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                controller.getBASE_URL()+URL_REGISTER,
                requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONObject userData = response.getJSONObject("data");
                                loading.setVisibility(View.GONE);
                                sessionManager.logout();
                                sessionManager.createSession(userData.getInt("id"), userData.getString("name"), userData.getString("email"), userData.getString("username"), userData.getString("profile"));
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                String message = response.getString("message");
                                loading.setVisibility(View.GONE);
                                signUpBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            signUpBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        loading.setVisibility(View.GONE);
                        signUpBtn.setVisibility(View.VISIBLE);
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
                                Log.d("RegisterActivity", "UnsupportedEncodingException: " + e.getMessage());
                            }
                        }
                        // Display the error message
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("RegisterActivity", "onErrorResponse: " + errorMessage);
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}