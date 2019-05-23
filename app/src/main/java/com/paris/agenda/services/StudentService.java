package com.paris.agenda.services;

import com.paris.agenda.modelo.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StudentService {

    @POST("aluno")
    Call<Void> insert (@Body Student student);
}
