package com.acquanero.ezcard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleResponse {

    @SerializedName("msg")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String token) {
        this.message = token;
    }

}