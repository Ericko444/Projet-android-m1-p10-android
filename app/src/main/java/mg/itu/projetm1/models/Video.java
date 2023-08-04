package mg.itu.projetm1.models;

public class Video{
    private int id;
    private int placeId;
    private String video;

    public Video() {
    }

    public Video(int placeId, String video) {
        this.placeId = placeId;
        this.video = video;
    }

    public Video(int id, int placeId, String video) {
        this.id = id;
        this.placeId = placeId;
        this.video = video;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
