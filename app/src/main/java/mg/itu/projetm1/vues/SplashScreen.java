package mg.itu.projetm1.vues;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.utils.DataCacheManager;
import mg.itu.projetm1.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private TextView version;
    private static String token;

    DataCacheManager dataCacheManager;

    List<Place> datas;

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        datas = new ArrayList<>();
        dataCacheManager = new DataCacheManager(getApplicationContext());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        setToken(token);
                        // Log and toast
                        Log.d("FCMTOKEN", token);
                    }
                });

        version = findViewById(R.id.version);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        try {
            version.setText("Version: "+getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        fetchPlaces();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    private void fetchPlaces(){
        List<Place> cachedData = dataCacheManager.loadData();
        if(cachedData != null && !cachedData.isEmpty()){
            datas.addAll(cachedData);
        }
        else{
            RetrofitClient.getRetrofitClient().getAllPlaces().enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        datas.addAll(response.body());
                        dataCacheManager.saveData(datas);
                    }
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {
                    Log.d("ERROR", "onFailure: "+t.getMessage());
                }


            });
        }

    }
}