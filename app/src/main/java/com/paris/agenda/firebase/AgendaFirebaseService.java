package com.paris.agenda.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.event.UpdateListStudentEvent;
import com.paris.agenda.retrofit.InitializerRetrofit;
import com.paris.agenda.sync.Synchroinize;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> message = remoteMessage.getData();
        Log.i("Mensagem Recebida", String.valueOf(message));

        convertToStudent(message);
    }

    private void convertToStudent(Map<String, String> message) {
        String accessKey = "alunoSync";
        if (message.containsKey(accessKey)){
            String json = message.get(accessKey);
            ObjectMapper mapper = new ObjectMapper();
            try {
                StudentSync studentSync = mapper.readValue(json, StudentSync.class);
               new Synchroinize(AgendaFirebaseService.this).synchronize(studentSync);
                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new UpdateListStudentEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
