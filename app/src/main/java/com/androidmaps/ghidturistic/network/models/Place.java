package com.androidmaps.ghidturistic.network.models;

import com.google.android.gms.maps.model.LatLng;
import java.util.Objects;

public class Place {

    private String uuid;
    private LatLng location;
    private String name;
    private String description;
    private String imageUrl;

    public Place(LatLng location, String name, String description, String imageUrl) {
        this.location = location;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        uuid = String.format("%d", hashCode());
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.uuid.equals(((Place) o).uuid);
    }

    @Override public int hashCode() {
        return Objects.hash(location, name, description, imageUrl);
    }
}
