package com.paris.agenda.services;

import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.modelo.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StudentService {

    @POST("aluno")
    Call<Void> insert (@Body Student student);

    @GET("aluno")
    Call<StudentSync> listStudent();

    @DELETE("aluno/{id}")
    Call<Void> delete(@Path("id") String id);
}
