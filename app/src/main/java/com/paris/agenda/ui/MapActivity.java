package com.paris.agenda.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.paris.agenda.MapFragment;
import com.paris.agenda.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fx = manager.beginTransaction();
        fx.replace(R.id.activity_map_frame, new MapFragment());
        fx.commit();
    }
}
