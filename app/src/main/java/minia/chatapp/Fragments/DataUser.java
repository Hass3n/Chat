package minia.chatapp.Fragments;

public class DataUser {
   private String name;
   private String national_id;
   private String  email;
   private  String image;
   private  String phone;


   public  DataUser()
   {

   }

    public DataUser(String name, String national_id, String email, String image, String phone) {
        this.name = name;
        this.national_id = national_id;
        this.email = email;
        this.image = image;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
