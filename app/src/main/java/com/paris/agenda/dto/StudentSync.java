package com.paris.agenda.dto;

import com.paris.agenda.modelo.Student;

import java.util.List;


public class StudentSync {

    private List<Student> alunos;
    private String momentoDaUltimaModificacao;

    public List<Student> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
