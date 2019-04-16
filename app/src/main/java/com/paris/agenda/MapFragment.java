package com.paris.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.modelo.Student;

import java.io.IOException;
import java.util.List;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        StudentDao dao = new StudentDao(getContext());

        for (Student student : dao.searchStudents()) {
            LatLng coordinates = searchCoordinatesAddress(student.getAddress());
            if (coordinates != null) {
                MarkerOptions marker = new MarkerOptions();
                marker.position(coordinates);
                marker.title(student.getName());
                marker.snippet(String.valueOf(student.getGrade()));
                googleMap.addMarker(marker);
            }
        }
        dao.close();


        LatLng position = searchCoordinatesAddress("Rua General Luis Barbedo, 167, Pirassununga");
        if (position != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 17);
            googleMap.moveCamera(update);
        }

    }

    private LatLng searchCoordinatesAddress(String address) {

        try {
            Geocoder geo = new Geocoder(getContext());
            List<Address> results =
                    geo.getFromLocationName(address, 1);
            if (!results.isEmpty()) {
                LatLng position = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
                return position;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
