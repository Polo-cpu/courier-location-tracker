package com.example.migrosone.courierTracking.utils;

public interface IGeoUtils {
    Double calculateDistanceForKM(double lat1, double lon1, double lat2, double lon2);
    Double calculateDistanceForMiles(double lat1, double lon1, double lat2, double lon2);
    Double calculateDistanceForMeters(double lat1, double lon1, double lat2, double lon2);
}
