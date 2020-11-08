package com.acquanero.ezcard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//clase modelo a la que se convierten los JSON

public class UserMailPass {

    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("password")
    @Expose
    private String password;

    public UserMailPass(String mail, String password){

        this.mail = mail;

        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}