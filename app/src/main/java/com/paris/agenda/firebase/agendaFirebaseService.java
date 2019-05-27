package com.paris.agenda.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.paris.agenda.retrofit.InitializerRetrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class agendaFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> message = remoteMessage.getData();
        Log.i("Mensagem Recebida", String.valueOf(message));
    }



    @Override
    public void onNewToken(String token) {
        Log.d("FirebaseToken", "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

        Call<Void> call = new InitializerRetrofit().getDeviceService().sendToken(token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.i("Token enviado", token);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("token Falhou", t.getMessage());

            }
        });

    }

}
