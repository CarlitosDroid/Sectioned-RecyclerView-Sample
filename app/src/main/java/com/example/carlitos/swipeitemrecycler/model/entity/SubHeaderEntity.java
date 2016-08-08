package com.example.carlitos.swipeitemrecycler.model.entity;

/**
 * Created by Carlos Vargas on 07/08/16.
 * Alias: CarlitosDroid
 */

public class SubHeaderEntity {

    private long id;
    private String title;

    public SubHeaderEntity(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
