package mg.itu.projetm1.models;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {
    private int id;
    private String title;
    private String desc;
    private double latitude;
    private  double longitude;
    private int provinceId;
    private int categorieId;

    private Categorie categorie;

    private Province province;

    private List<Tag> tags;

    private List<Review> reviews;

    private List<Image> images;

    private List<Video> videos;

    public Place(int id, String title, String desc, double latitude, double longitude, int provinceId, int categorieId) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provinceId = provinceId;
        this.categorieId = categorieId;
    }

    public Place(String title, String desc, double latitude, double longitude, int provinceId, int categorieId) {
        this.title = title;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provinceId = provinceId;
        this.categorieId = categorieId;
    }

    public Place(int id, String title, String desc, double latitude, double longitude, int provinceId, int categorieId, Categorie categorie, Province province, List<Tag> tags, List<Review> reviews, List<Image> images, List<Video> videos) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provinceId = provinceId;
        this.categorieId = categorieId;
        this.categorie = categorie;
        this.province = province;
        this.tags = tags;
        this.reviews = reviews;
        this.images = images;
        this.videos = videos;
    }

    public Place(String title, String desc, double latitude, double longitude, int provinceId, int categorieId, Categorie categorie, Province province, List<Tag> tags, List<Review> reviews, List<Image> images, List<Video> videos) {
        this.title = title;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provinceId = provinceId;
        this.categorieId = categorieId;
        this.categorie = categorie;
        this.province = province;
        this.tags = tags;
        this.reviews = reviews;
        this.images = images;
        this.videos = videos;
    }

    public Place() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
