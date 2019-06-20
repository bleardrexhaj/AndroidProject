package com.br.projekt.internshipproject;

/**
 * Created by Brex on 6/20/2019.
 */

public class UserModel {
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private int id;

    public UserModel(){

    }

    public UserModel(String avatar,String firstName, String lastName, int id){
        this.id = 1;
        this.firstName = "hajdar";
        this.lastName = "duushi";
        avatar = "https://images.idgesg.net/images/article/2019/04/google-shift-100794036-large.jpg";
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.firstName+" "+this.lastName;
    }
}
