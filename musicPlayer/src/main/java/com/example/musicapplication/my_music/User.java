package com.example.musicapplication.my_music;

import android.media.Image;

public class User {
    public static final Image DEFAULT_AVAR = null;
    public static final String DEFAULT_KEY = "666666";
    private String name;
    private String id, key;
    private Image avar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getAvar() {
        return avar;
    }

    public void setAvar(Image avar) {
        this.avar = avar;
    }

    public User(String name, String id, Image avar) {
        this(name, id, DEFAULT_KEY, avar);
    }

    public User(String name, String id, String key, Image avar){
        this.name = name;
        this.id = id;
        this.key = key;
        this.avar = (avar == null? DEFAULT_AVAR : avar);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(String name, String id, String key){
        this(name, id, key, DEFAULT_AVAR);
    }

    public User(String name, String id){
        this(name, id, DEFAULT_AVAR);
    }

    public String getGeneralInfo(){
        return String.format("%s&%s", this.id, this.name);
    }
}
