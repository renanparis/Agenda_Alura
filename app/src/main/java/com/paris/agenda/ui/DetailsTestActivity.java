package com.paris.agenda.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.paris.agenda.R;
import com.paris.agenda.modelo.Test;

import java.util.List;

public class DetailsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_test);

        Intent intent = getIntent();
        Test test = (Test) intent.getSerializableExtra("test");

        TextView fieldMatter = findViewById(R.id.activity_details_test_matter);
        fieldMatter.setText(test.getMatter());
        TextView fieldDate = findViewById(R.id.activity_details_test_date);
        fieldDate.setText(test.getDate());
        List<String> themes = test.getThemes();
        ListView listThemes = findViewById(R.id.activity_details_test_themes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, themes);

        listThemes.setAdapter(adapter);



    }
}
