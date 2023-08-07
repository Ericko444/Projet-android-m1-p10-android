package mg.itu.projetm1.utils;

import android.app.Application;

import com.squareup.picasso.Picasso;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Picasso's logging
        Picasso.get().setLoggingEnabled(true);
    }
}
