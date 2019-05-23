package com.paris.agenda.retrofit;

import com.paris.agenda.services.StudentService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class InitializerRetrofit {

    private final Retrofit retrofit;


    public InitializerRetrofit() {

        retrofit=  new Retrofit.Builder().baseUrl("http://192.168.0.103:8080/api/")
                 .addConverterFactory(JacksonConverterFactory.create())
                 .build();
    }

    public StudentService getStudentService() {
        return retrofit.create(StudentService.class);
    }
}
