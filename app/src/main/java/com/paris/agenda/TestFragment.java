package com.paris.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.paris.agenda.modelo.Test;
import com.paris.agenda.ui.DetailsTestActivity;
import com.paris.agenda.ui.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_test, container, false);
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

        ArrayAdapter<Test> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, tests);
        ListView list = view.findViewById(R.id.activity_list_tests);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Test test = (Test) parent.getItemAtPosition(position);

               TestActivity testActivity = (TestActivity) getActivity();
               testActivity.selectTest(test);
            }
        });


        return view;
    }
}
