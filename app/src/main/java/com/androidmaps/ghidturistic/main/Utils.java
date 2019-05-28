package com.androidmaps.ghidturistic.main;

public class Utils {

    private static final String EAST = "E";
    private static final String WEST = "W";
    private static final String NORTH = "N";
    private static final String SOUTH = "S";

    private static final String COORD_FORMAT = "%f %s";

    private static Utils INSTANCE = null;

    private Utils() {}

    public static Utils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

    public String getLatitudeString(double lat) {
        if (lat >= 0) {
            return String.format(COORD_FORMAT, lat, NORTH);
        }
        return String.format(COORD_FORMAT, Math.abs(lat), SOUTH);
    }

    public String getLongitudeString(double lng) {
        if (lng >= 0) {
            return String.format(COORD_FORMAT, lng, EAST);
        }
        return String.format(COORD_FORMAT, Math.abs(lng), WEST);
    }
}
