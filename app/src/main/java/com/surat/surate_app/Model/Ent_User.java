package com.surat.surate_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_User {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("level")
    @Expose
    private String level;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("pesan")
    @Expose
    private String pesan;

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
