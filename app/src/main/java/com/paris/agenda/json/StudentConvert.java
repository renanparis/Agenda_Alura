package com.paris.agenda.json;

import com.paris.agenda.modelo.Student;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class StudentConvert {


    public String convertToJSON(List<Student> students) {

        JSONStringer js = new JSONStringer();
        try {
            js.object().key("List").array().object().key("student").array();
            for (Student student: students) {
                js.object();
                js.key("name").value(student.getName());
                js.key("grade").value(student.getGrade());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
