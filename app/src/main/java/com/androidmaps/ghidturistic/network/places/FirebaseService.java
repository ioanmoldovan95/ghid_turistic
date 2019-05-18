package com.androidmaps.ghidturistic.network.places;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidmaps.ghidturistic.main.MapCallback;
import com.androidmaps.ghidturistic.network.models.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Context context;
    private MapCallback mapCallback;
    private PlacesManager placesManager;

    public FirebaseService(Context context, MapCallback mapCallback, PlacesManager placesManager) {
        this.context = context;
        this.mapCallback = mapCallback;
        this.placesManager = placesManager;
        initDatabase();
        initPlaceTypes();
        loadDatabase();
    }

    public void loadDatabase() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference placesReference = firebaseDatabase.getReference("places");

        placesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Place> places = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Place place = childSnapshot.getValue(Place.class);
                    places.add(place);
                    mapCallback.onPlaceLoaded(place, false);
                }
                placesManager.setPlaces(places);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference typesReference = firebaseDatabase.getReference("placeTypes");

        typesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> types = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String type = childSnapshot.getValue(String.class);
                    types.add(type);
                }
                placesManager.setPlaceTypes(types);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void requestLocationPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initDatabase() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("places");

        final List<Place> places = new ArrayList<>();
        places.add(new Place(new LatLng(47, 31), "New place 1", "this is the first new place", "", null));
        places.add(new Place(new LatLng(43, 53), "New place 2", "this is the first new place", "", null));
        places.add(new Place(new LatLng(64, 31), "New place 3", "this is the first new place", "", null));
        places.add(new Place(new LatLng(57, 31), "New place 4", "this is the first new place", "", null));
        places.add(new Place(new LatLng(108, 31), "New place 5", "this is the first new place", "", null));
        places.add(new Place(new LatLng(44, 31), "New place 6", "this is the first new place", "", null));
        places.add(new Place(new LatLng(42, 31), "New place 7", "this is the first new place", "", null));
        places.add(new Place(new LatLng(43, 31), "New place 8", "this is the first new place", "", null));
        places.add(new Place(new LatLng(68, 31), "New place 9", "this is the first new place", "", null));
        places.add(new Place(new LatLng(34, 31), "New place 10", "this is the first new place", "", null));

        databaseReference.setValue(places);
    }

    private void initPlaceTypes() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("placeTypes");

        final List<String> types = new ArrayList<String>();
        types.add("Restaurant");
        types.add("Pub");
        types.add("Architecture");
        types.add("Church");
        types.add("Fun");

        databaseReference.setValue(types);
    }
}
