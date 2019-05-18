package com.androidmaps.ghidturistic.network.places;

import com.androidmaps.ghidturistic.network.models.Place;
import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class PlacesManager {
    private List<Place> places;
    private List<String> placeTypes;
    private List<Place> searchResults;

    private static PlacesManager INSTANCE;

    public static PlacesManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlacesManager();
        }
        return INSTANCE;
    }

    private PlacesManager() {
        places = new ArrayList<>();
        placeTypes = new ArrayList<>();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<String> getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(List<String> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public List<Place> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Place> searchResults) {
        this.searchResults = searchResults;
    }


    public List<Place> getPlaces(List<Place> filteredPlaces, String searchTerm) {
        final List<Place> searchItems = filteredPlaces != null ? filteredPlaces : places;
        if (Strings.isEmptyOrWhitespace(searchTerm)) {
            return searchItems;
        }
        final List<Place> results = new ArrayList<>();
        for (Place place : searchItems) {
            if (place.getName().contains(searchTerm)) {
                results.add(place);
            }
        }
        return results;
    }

    public List<Place> getPlaces(List<Place> filteredPlaces, String latText, String lngText) {
        final List<Place> searchItems = filteredPlaces != null ? filteredPlaces : places;
        if (Strings.isEmptyOrWhitespace(latText) || Strings.isEmptyOrWhitespace(lngText)) {
            return searchItems;
        }
        float lat = Float.parseFloat(latText);
        float lng = Float.parseFloat(lngText);
        final List<Place> results = new ArrayList<>();
        for (Place place : searchItems) {
            if (Math.abs(place.getLatitude() - lat) < 1 &&
                    Math.abs(place.getLongitude() - lng) < 1) {
                results.add(place);
            }
        }
        return results;
    }

    public List<Place> getPlaces(List<Place> filteredPlaces, List<String> placeTypes) {
        final List<Place> searchItems = filteredPlaces != null ? filteredPlaces : places;
        if (placeTypes.isEmpty()) {
            return searchItems;
        }
        final List<Place> results = new ArrayList<>();
        for (String type : placeTypes) {
            for (Place place : searchItems) {
                if (!results.contains(place) && place.getTypes().contains(type)) {
                    results.add(place);
                }
            }
        }
        return results;
    }
}
