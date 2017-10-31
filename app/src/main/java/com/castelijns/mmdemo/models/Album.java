package com.castelijns.mmdemo.models;

import android.support.annotation.NonNull;

public class Album implements Comparable<Album> {

    private int id;
    private int userId;
    private String title;

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

    @Override
    public int compareTo(@NonNull Album o) {
        return title.compareTo(o.getTitle());
    }
}
