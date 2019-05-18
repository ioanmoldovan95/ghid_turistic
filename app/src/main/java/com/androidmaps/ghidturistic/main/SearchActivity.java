package com.androidmaps.ghidturistic.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.androidmaps.ghidturistic.R;
import com.androidmaps.ghidturistic.network.models.Place;
import com.androidmaps.ghidturistic.network.places.PlacesManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    private List<String> types;
    private SearchView searchView;
    private EditText latitudeEditText, longitudeEditText;
    private Button searchButton;
    private LinearLayout leftColumn, rightColumn;

    private PlacesManager placesManager = PlacesManager.getInstance();

    private List<String> selectedTypes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        initViews();
        types = placesManager.getPlaceTypes();
        initTypes();
    }

    private void initViews() {
        searchView = findViewById(R.id.search_view);
        searchButton = findViewById(R.id.search);
        latitudeEditText = findViewById(R.id.latitude_edittext);
        longitudeEditText = findViewById(R.id.longitude_edittext);
        leftColumn = findViewById(R.id.left_column);
        rightColumn = findViewById(R.id.right_column);

        searchButton.setOnClickListener(v -> performSearch());
    }

    private void initTypes() {
        for (int i = 0; i < types.size(); i++) {
            if (i % 2 == 0) {
                leftColumn.addView(getCheckbox(types.get(i)));
            } else {
                rightColumn.addView(getCheckbox(types.get(i)));
            }
        }
    }

    private View getCheckbox(String type) {
        CheckBox checkBox = new CheckBox(getApplicationContext());
        checkBox.setText(type);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedTypes.add(type);
            } else {
                if (selectedTypes.contains(type)) {
                    selectedTypes.remove(type);
                }
            }
        });
        return checkBox;
    }

    private void performSearch() {
        String searchTerm = searchView.getQuery().toString();

        String latText = latitudeEditText.getText().toString();
        String lngText = longitudeEditText.getText().toString();

        List<Place> results = placesManager.getPlaces(null, searchTerm);
        results = placesManager.getPlaces(results, latText, lngText);

        results = placesManager.getPlaces(results, selectedTypes);

        placesManager.setSearchResults(results);

        setResult(RESULT_OK);
        this.finish();
    }
}
