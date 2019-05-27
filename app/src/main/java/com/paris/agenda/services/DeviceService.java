package com.paris.agenda.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DeviceService {

    @POST("firebase/dispositivo")
    Call<Void> sendToken(@Header("token") String token);
}
