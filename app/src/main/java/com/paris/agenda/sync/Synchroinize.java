package com.paris.agenda.sync;

import android.content.Context;
import android.util.Log;

import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.event.UpdateListStudentEvent;
import com.paris.agenda.preferences.StudentPreferences;
import com.paris.agenda.retrofit.InitializerRetrofit;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Synchroinize {


    private final Context context;
    private final EventBus eventBus = EventBus.getDefault();

    public Synchroinize(Context context) {
        this.context = context;
    }

    public void loadServeList() {
        Call<StudentSync> listStudent = new InitializerRetrofit().getStudentService().listStudent();
        listStudent.enqueue(new Callback<StudentSync>() {
            @Override
            public void onResponse(Call<StudentSync> call, Response<StudentSync> response) {
                StudentSync studentSync = response.body();

                String version = studentSync.getMomentoDaUltimaModificacao();
                StudentPreferences preferences = new StudentPreferences(context);
                preferences.saveVersion(version);

                StudentDao studentDao = new StudentDao(context);
                studentDao.synchronize(studentSync.getAlunos());
                studentDao.close();

                Log.i("Versão", preferences.getVersion());

                eventBus.post(new UpdateListStudentEvent());

            }

            @Override
            public void onFailure(Call<StudentSync> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
                eventBus.post(new UpdateListStudentEvent());



            }
        });
    }
}