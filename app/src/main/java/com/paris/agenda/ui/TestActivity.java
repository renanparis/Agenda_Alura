package com.paris.agenda.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.paris.agenda.DetailsTestFragment;
import com.paris.agenda.R;
import com.paris.agenda.TestFragment;
import com.paris.agenda.modelo.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fx = fragmentManager.beginTransaction();
        fx.replace(R.id.activity_test_frame, new TestFragment());
        if (confirmLandscapeMode()) {
            fx.replace(R.id.activity_details_test_frame, new DetailsTestFragment());
        }

        fx.commit();


    }

    private boolean confirmLandscapeMode() {
        return getResources().getBoolean(R.bool.landscapemode);
    }


    public void selectTest(Test test) {

        FragmentManager manager = getSupportFragmentManager();
        if (!confirmLandscapeMode()) {

            FragmentTransaction fx = manager.beginTransaction();
            DetailsTestFragment detailsFragment = new DetailsTestFragment();
            Bundle params = new Bundle();
            params.putSerializable("test", test);
            detailsFragment.setArguments(params);
            fx.replace(R.id.activity_test_frame, detailsFragment);
            fx.addToBackStack(null);
            fx.commit();
        } else {
            DetailsTestFragment detailsTestFragment =
                    (DetailsTestFragment) manager.findFragmentById(R.id.activity_details_test_frame);
            detailsTestFragment.fillField(test);
        }
    }
}
