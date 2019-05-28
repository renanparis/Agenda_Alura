package com.paris.agenda.services;

import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.modelo.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {

    @POST("aluno")
    Call<Void> insert(@Body Student student);

    @GET("aluno")
    Call<StudentSync> listStudent();

    @DELETE("aluno/{id}")
    Call<Void> delete(@Path("id") String id);

    @GET("aluno/diff")
    Call<StudentSync> newStudent(@Header("datahora") String version);

    @PUT("aluno/lista")
    Call<StudentSync> updateListStudent(@Body List<Student> students);
}
