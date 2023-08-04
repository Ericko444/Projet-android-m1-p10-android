package mg.itu.projetm1.models;

import java.io.Serializable;

public class Tag implements Serializable {
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

    public Tag() {
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
