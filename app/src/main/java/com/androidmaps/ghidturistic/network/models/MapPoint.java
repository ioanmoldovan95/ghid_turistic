package com.androidmaps.ghidturistic.network.models;

import com.google.android.gms.maps.model.LatLng;

public class MapPoint {

    private LatLng location;

    private String name;

    private String description;

    private String imageUrl;

    public MapPoint(LatLng location, String name, String description, String imageUrl) {
        this.location = location;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
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
}
