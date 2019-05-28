package com.androidmaps.ghidturistic.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.androidmaps.ghidturistic.R;
import com.androidmaps.ghidturistic.network.models.Place;
import com.androidmaps.ghidturistic.network.places.PlacesManager;
import com.squareup.picasso.Picasso;

public class PlaceDetailsActivity extends Activity {

    public static final String BUNDLE_PLACE_UUID = "bundle_place_uuid";

    private TextView nameTextView, latTextView, lngTextView, descriptionTextView;
    private ImageView placeImageView;
    private Utils utils;
    private PlacesManager placesManager;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        initFields();

        final String placeUuid = this.getIntent().getExtras().getString(BUNDLE_PLACE_UUID);
        place = placesManager.getPlaceByUuid(placeUuid);

        bindFields();
    }

    private void bindFields() {
        nameTextView.setText(place.getName());
        latTextView.setText(utils.getLatitudeString(place.getLatitude()));
        lngTextView.setText(utils.getLongitudeString(place.getLongitude()));
        descriptionTextView.setText(place.getDescription());
        Picasso.get().load(place.getImageUrl()).into(placeImageView);
    }

    private void initFields() {
        nameTextView = findViewById(R.id.place_name);
        latTextView = findViewById(R.id.lat_text_view);
        lngTextView = findViewById(R.id.lng_text_view);
        descriptionTextView = findViewById(R.id.description);
        placeImageView = findViewById(R.id.place_image);
        utils = Utils.getInstance();
        placesManager = PlacesManager.getInstance();
    }
}
