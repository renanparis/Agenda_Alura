package com.paris.agenda;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


public class Locator extends LocationCallback {

    private final GoogleMap map;
    private FusedLocationProviderClient client;
    private Context context;

    public Locator(Context context , GoogleMap map) {

        this.client = LocationServices.getFusedLocationProviderClient(context);
        this.map = map;


    }

    @SuppressLint("MissingPermission")
    public void conected (){
        LocationRequest request = new LocationRequest();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(20);
        client.requestLocationUpdates(request, this, null);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);

        locationResult.getLastLocation();




    }

    public void cancelSearch (){
        client.removeLocationUpdates(this);
    }
}
