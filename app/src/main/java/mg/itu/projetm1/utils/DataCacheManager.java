package mg.itu.projetm1.utils;



import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

import mg.itu.projetm1.models.Place;

public class DataCacheManager {

    private static final String PREF_NAME = "MyDataCache";
    private static final String KEY_DATA = "cached_data";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public DataCacheManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveData(List<Place> data) {
        String jsonData = gson.toJson(data);
        sharedPreferences.edit().putString(KEY_DATA, jsonData).apply();
    }

    public List<Place> loadData() {
        String jsonData = sharedPreferences.getString(KEY_DATA, null);
        if (jsonData != null) {
            Type listType = new TypeToken<List<Place>>(){}.getType();
            return gson.fromJson(jsonData, listType);
        }
        return null;
    }

    public List<Place> recommendations(){
        List<Place> data = loadData();
        if(data != null){
            return PlaceReviewUtils.getTopPlacesWithHighestSumOfNote(data);
        }
        return null;
    }
}