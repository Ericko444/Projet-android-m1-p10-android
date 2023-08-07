package mg.itu.projetm1.models;

public class Categorie {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie() {
    }

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
