package com.example.carlitos.swipeitemrecycler.model.entity;

/**
 * Created by Carlos Vargas on 07/08/16.
 * Alias: CarlitosDroid
 */

public class ItemEntity {
    private long id;
    private boolean isSectionHeader;
    private String name;
    private String description;
    private boolean pinned;

    public ItemEntity(long id, boolean isSectionHeader, String name, String description, boolean pinned) {
        this.id = id;
        this.isSectionHeader = isSectionHeader;
        this.name = name;
        this.description = description;
        this.pinned = pinned;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSectionHeader() {
        return isSectionHeader;
    }

    public void setSectionHeader(boolean sectionHeader) {
        isSectionHeader = sectionHeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
