package mg.itu.projetm1.interfaces;

import java.util.List;

import mg.itu.projetm1.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceInterface {

    @GET("/api/places")
    Call<List<Place>> getPlaces();
}
