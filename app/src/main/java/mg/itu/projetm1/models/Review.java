package mg.itu.projetm1.models;

import java.io.Serializable;

public class Review implements Serializable {
    private int id;
    private int userId;
    private int placeId;
    private int note;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Review() {
    }

    public Review(int userId, int placeId, int note, String comment) {
        this.userId = userId;
        this.placeId = placeId;
        this.note = note;
        this.comment = comment;
    }

    public Review(int id, int userId, int placeId, int note, String comment) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.note = note;
        this.comment = comment;
    }
}
