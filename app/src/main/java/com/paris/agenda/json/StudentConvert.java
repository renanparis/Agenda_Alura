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
            for (Student student : students) {
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

    public String convertToJSONCompleteStudent(Student student) {
        JSONStringer js = new JSONStringer();
        try {
            js.object().key("nome").value(student.getName())
                    .key("endereco").value(student.getAddress())
                    .key("site").value(student.getSite())
                    .key("telefone").value(student.getPhone())
                    .key("nota").value(student.getGrade())
                    .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  js.toString();
    }
}
