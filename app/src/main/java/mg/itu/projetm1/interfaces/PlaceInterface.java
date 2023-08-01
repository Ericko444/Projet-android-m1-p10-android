package mg.itu.projetm1.interfaces;

import java.util.List;

import mg.itu.projetm1.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceInterface {

    @GET("/api/recommendations")
    Call<List<Place>> getPlacesRecommendation();

    @GET("/api/parprovinces")
    Call<List<Place>> getPlacesParProvince();
}
