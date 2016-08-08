package com.example.carlitos.swipeitemrecycler.model.entity;

/**
 * Created by Carlos Vargas on 07/08/16.
 * Alias: CarlitosDroid
 */

public class MainHeaderEntity {

    private long id;
    private String name;
    private String age;
    private String address;

    public MainHeaderEntity(long id, String name, String age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
