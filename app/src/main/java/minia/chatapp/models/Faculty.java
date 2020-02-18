package minia.chatapp.models;

public class Faculty {

    private long id;
    private String name;
    private long university_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(long university_id) {
        this.university_id = university_id;
    }
}
