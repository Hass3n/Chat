package minia.chatapp.models;

public class Data {

    public String name;
    public String status;
    public String image;
    public String thumb_image;
    public String type;
    public  String key;
    public  String u_name;
    public String f_name;

    Data(){ }

    public Data(String name, String status, String image, String thumb_image, String type, String key, String u_name, String f_name) {

        this.name = name;
        this.status = status;
        this.image = image;
        this.thumb_image = thumb_image;
        this.type = type;
        this.key = key;
        this.u_name = u_name;
        this.f_name = f_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
}
