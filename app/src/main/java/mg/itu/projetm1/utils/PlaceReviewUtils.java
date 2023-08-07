package mg.itu.projetm1.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.models.Review;

public class PlaceReviewUtils {

    public static List<Place> getTopPlacesWithHighestSumOfNote(List<Place> places) {
        List<Place> topPlaces = new ArrayList<>(places);

        for (Place place : topPlaces) {
            int sumOfNotes = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sumOfNotes = place.getReviews().stream()
                        .mapToInt(Review::getNote)
                        .sum();
            }
            place.setSumOfNotes(sumOfNotes);
        }

        Collections.sort(topPlaces, new Comparator<Place>() {
            @Override
            public int compare(Place place1, Place place2) {
                return Integer.compare(place2.getSumOfNotes(), place1.getSumOfNotes());
            }
        });

        if (topPlaces.size() > 5) {
            return topPlaces.subList(0, 5);
        } else {
            return topPlaces;
        }
    }
}
