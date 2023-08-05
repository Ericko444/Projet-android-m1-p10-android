package mg.itu.projetm1.models;

public class Notification {
    int id;
    int userId;
    String title;
    String body;
    String date;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Notification() {
    }

    public Notification(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public Notification(int userId, String title, String body, String date) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
