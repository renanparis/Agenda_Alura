package com.paris.agenda.json;

import com.paris.agenda.modelo.Student;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class StudentConvert {


    public String convertToJSON(List<Student> students) {

        JSONStringer js = new JSONStringer();
        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Student student: students) {
                js.object();
                js.key("nome").value(student.getName());
                js.key("nota").value(student.getGrade());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
