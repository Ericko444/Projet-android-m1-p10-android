package mg.itu.projetm1.interfaces;

import java.util.List;

import mg.itu.projetm1.models.Notification;
import mg.itu.projetm1.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlaceInterface {

    @GET("/api/recommendations")
    Call<List<Place>> getPlacesRecommendation();

    @GET("/api/parprovinces")
    Call<List<Place>> getPlacesParProvince();

    @GET("/api/places")
    Call<List<Place>> getAllPlaces();

    @GET("/api/datas")
    Call<List<List<Place>>> getDatas();

    @GET("/api/notifications/{id}")
    Call<List<Notification>> getNotifications(@Path("id") int id);

}
