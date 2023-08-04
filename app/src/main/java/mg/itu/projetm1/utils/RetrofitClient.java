package mg.itu.projetm1.utils;

import mg.itu.projetm1.interfaces.PlaceInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://7ae1-154-126-56-74.ngrok-free.app/";
    private static Retrofit retrofit = null;

    public static PlaceInterface getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(PlaceInterface.class);
    }
}