package com.androidmaps.ghidturistic.main;

import com.androidmaps.ghidturistic.network.models.Place;

public interface MapCallback {
    void onPlaceLoaded(Place place, boolean move);
}
