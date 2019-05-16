package com.androidmaps.ghidturistic.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.androidmaps.ghidturistic.R;
import com.androidmaps.ghidturistic.network.models.Place;
import com.androidmaps.ghidturistic.network.places.FirebaseService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapCallback {

    private GoogleMap mMap;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        firebaseService = new FirebaseService(this, this);
        firebaseService.requestLocationPermission(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37, 25);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Romania"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Log.d("Map", "Map loaded");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FirebaseService.LOCATION_PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override public void onPlaceLoaded(Place place) {
        LatLng location = new LatLng(place.getLatitude(), place.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
    }
}
