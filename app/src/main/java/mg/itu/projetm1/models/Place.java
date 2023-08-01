package mg.itu.projetm1.models;

public class Place {
    private int id;
    private String title;
    private String desc;
    private double latitude;
    private  double longitude;
    private int provinceId;
    private int categorieId;

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
}
