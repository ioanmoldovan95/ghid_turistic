package com.androidmaps.ghidturistic.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import com.androidmaps.ghidturistic.R;
import com.androidmaps.ghidturistic.network.models.Place;
import com.androidmaps.ghidturistic.network.places.FirebaseService;
import com.androidmaps.ghidturistic.network.places.PlacesManager;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapCallback {

    private static final int SEARCH_REQUEST_CODE = 10;

    private GoogleMap mMap;
    private FirebaseService firebaseService;
    private PlacesManager placesManager;
    private SearchView searchView;
    private ImageView resetButton;

    private boolean myLocationMarkerAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        firebaseService = new FirebaseService(this, this, PlacesManager.getInstance());
        firebaseService.requestLocationPermission(this);
        placesManager = PlacesManager.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchView = findViewById(R.id.search_bar);
        searchView.setOnClickListener(v -> startSearchActivity());
        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> resetMap());
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(arg0 -> {
            if (!myLocationMarkerAdded) {
                final LatLng location = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title("It's Me!"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f));
            }
            myLocationMarkerAdded = true;
        });
        mMap.setOnInfoWindowClickListener(marker -> {
            String uuid = (String) marker.getTag();
            if(!Strings.isEmptyOrWhitespace(uuid)) {
                startPlaceDetailsActivity(uuid);
            }
        });
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    private void startPlaceDetailsActivity(String uuid) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PlaceDetailsActivity.BUNDLE_PLACE_UUID, uuid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FirebaseService.LOCATION_PERMISSION_REQUEST_CODE) {
                firebaseService.loadDatabase();
            }
            if (requestCode == SEARCH_REQUEST_CODE) {
                setSearchResults();
            }
        }
    }

    private void setSearchResults() {
        final List<Place> searchResults = placesManager.getSearchResults();
        mMap.clear();
        if (searchResults != null) {
            for (Place place : searchResults) {
                onPlaceLoaded(place, false);
            }
        }
    }

    @Override
    public void onPlaceLoaded(Place place, boolean moveToLocation) {
        if (mMap != null) {
            LatLng location = new LatLng(place.getLatitude(), place.getLongitude());
            final Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
            marker.setTag(place.getUuid());
            if (moveToLocation) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
            }
        }
    }

    private void startSearchActivity() {
        final Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }

    private void resetMap() {
        final List<Place> places = placesManager.getPlaces();
        for (Place place : places) {
            onPlaceLoaded(place, false);
        }
    }

}
