package com.paris.agenda.task;

import android.os.AsyncTask;

import com.paris.agenda.WebClient;
import com.paris.agenda.json.StudentConvert;
import com.paris.agenda.modelo.Student;

public class InsertStudentTask extends AsyncTask {


    private final Student student;

    public InsertStudentTask(Student student) {

        this.student = student;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        String json = new StudentConvert().convertToJSONCompleteStudent(student);

        new WebClient().insert(json);
        return null;
    }
}
