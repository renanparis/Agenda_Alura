package com.paris.agenda.dto;

import com.paris.agenda.modelo.Student;

import java.util.List;

public class StudentSync {

    private List<Student> students;
    private String momentoDaUltimaModificacao;

    public List<Student> getStudents() {
        return students;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
