package com.paris.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.paris.agenda.modelo.Test;

public class DetailsTestFragment extends Fragment {

    private TextView fieldMatter;
    private TextView fieldDate;
    private ListView fieldThemes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_test, container, false);

        fieldMatter = view.findViewById(R.id.activity_details_test_matter);
        fieldDate = view.findViewById(R.id.activity_details_test_date);
        fieldThemes = view.findViewById(R.id.activity_details_test_themes);

        Bundle params = getArguments();
        if (params != null){
            Test test = (Test) params.getSerializable("test");
            fillField(test);
        }
        return view;
    }

    public void fillField(Test test){
        fieldMatter.setText(test.getMatter());
        fieldDate.setText(test.getDate());
        ArrayAdapter<String> adapterThemes =
                new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, test.getThemes());
        fieldThemes.setAdapter(adapterThemes);


    }
}
