package com.paris.agenda.retrofit;

import com.paris.agenda.services.StudentService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class InitializerRetrofit {

    private final Retrofit retrofit;


    public InitializerRetrofit() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);


        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.104:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public StudentService getStudentService() {
        return retrofit.create(StudentService.class);
    }
}
