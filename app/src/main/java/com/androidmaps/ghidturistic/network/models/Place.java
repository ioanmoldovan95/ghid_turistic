package com.androidmaps.ghidturistic.network.models;

import com.google.android.gms.maps.model.LatLng;
import java.util.Objects;

public class Place {

    private String uuid;
    private String name;
    private String description;
    private String imageUrl;
    private double latitude;
    private double longitude;

    public Place() {}

    public Place(LatLng location, String name, String description, String imageUrl) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        uuid = String.format("%d", hashCode());
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
        return Objects.hash(latitude, longitude, name, description, imageUrl);
    }
}
