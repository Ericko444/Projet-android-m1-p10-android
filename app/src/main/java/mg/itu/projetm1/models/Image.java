package mg.itu.projetm1.models;

import java.io.Serializable;

public class Image implements Serializable {
    private int id;
    private int placeId;
    private String image;

    public Image() {
    }

    public Image(int placeId, String image) {
        this.placeId = placeId;
        this.image = image;
    }

    public Image(int id, int placeId, String image) {
        this.id = id;
        this.placeId = placeId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
