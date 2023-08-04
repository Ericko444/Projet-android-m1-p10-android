package mg.itu.projetm1.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Users {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String profile;

    public Users() {}

    public Users(String username, String name, String email, String password, String profile) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public Users(Integer id, String username, String name, String email, String password, String profile) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public Users(Integer id, String username, String name, String email, String profile) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public static Users fromJSON(JSONObject userData) throws JSONException{
        Users user = null;
        try {
            Integer id = userData.getInt("id");
            String username = userData.getString("username").trim();
            String name = userData.getString("name").trim();
            String email = userData.getString("email").trim();
            String profile = userData.getString("profile").trim();

            user = new Users(id, username, name, email, profile);

        } catch (JSONException e) {
            throw e;
        } finally {
            return user;
        }

    }
}
