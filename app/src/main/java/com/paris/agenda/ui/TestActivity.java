package com.paris.agenda.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.paris.agenda.R;
import com.paris.agenda.modelo.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<String> themesPortguese = new ArrayList<>();
        List<String> themesMath = new ArrayList<>();
        themesPortguese.add("Sujeito");
        themesPortguese.add("Objeto");
        themesPortguese.add("Predicado");
        themesMath.add("Algebra");
        themesMath.add("Matriz");
        themesMath.add("Vetores");

        List<Test> tests = new ArrayList<>();

        Test portuguese = new Test("Português", "25/04/2019",themesPortguese );
        Test math = new Test("Matemática", "26/04/2019", themesMath);

        tests.add(portuguese);
        tests.add(math);

        ArrayAdapter<Test> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tests);
         ListView list = findViewById(R.id.activity_list_tests);
         list.setAdapter(adapter);

         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Test test = (Test) parent.getItemAtPosition(position);
                 Intent goToDetails = new Intent(TestActivity.this, DetailsTestActivity.class);
                 goToDetails.putExtra("test", test);
                 startActivity(goToDetails);
             }
         });


    }


}
