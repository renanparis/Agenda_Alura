package com.paris.agenda.sync;

import android.content.Context;
import android.util.Log;

import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.event.UpdateListStudentEvent;
import com.paris.agenda.modelo.Student;
import com.paris.agenda.preferences.StudentPreferences;
import com.paris.agenda.retrofit.InitializerRetrofit;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Synchroinize {


    private final Context context;
    private final EventBus eventBus = EventBus.getDefault();
    private StudentPreferences preferences;

    public Synchroinize(Context context) {
        this.context = context;
        preferences = new StudentPreferences(context);
    }

    public void loadAllStudents() {

        if (preferences.hasVersion()) {
            loadNewStudents();

        } else {
            loadServeList();
        }
    }

    private void loadNewStudents() {
        String version = preferences.getVersion();
        Call<StudentSync> call = new InitializerRetrofit().getStudentService().newStudent(version);
        call.enqueue(getCallback());

    }


    private void loadServeList() {
        Call<StudentSync> call = new InitializerRetrofit().getStudentService().listStudent();
        call.enqueue(getCallback());
    }

    private Callback<StudentSync> getCallback() {
        return new Callback<StudentSync>() {
            @Override
            public void onResponse(Call<StudentSync> call, Response<StudentSync> response) {
                StudentSync studentSync = response.body();

                String version = studentSync.getMomentoDaUltimaModificacao();
                preferences.saveVersion(version);

                StudentDao studentDao = new StudentDao(context);
                studentDao.synchronize(studentSync.getAlunos());
                studentDao.close();

                Log.i("Versão", preferences.getVersion());

                eventBus.post(new UpdateListStudentEvent());

                loadStudentsNotsynced();



            }

            @Override
            public void onFailure(Call<StudentSync> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
                eventBus.post(new UpdateListStudentEvent());



            }
        };
    }
    
    private void loadStudentsNotsynced(){
        StudentDao dao = new StudentDao(context);
        List<Student> students = dao.studentsNotSynced();
        Call<StudentSync> call= new InitializerRetrofit().getStudentService().updateListStudent(students);
        call.enqueue(new Callback<StudentSync>() {
            @Override
            public void onResponse(Call<StudentSync> call, Response<StudentSync> response) {
                StudentSync studentSync = response.body();
                dao.synchronize(studentSync.getAlunos());
                dao.close();
            }

            @Override
            public void onFailure(Call<StudentSync> call, Throwable t) {

            }
        });
    }

    public void deleteStudentServer(Student student) {

        Call<Void> call = new InitializerRetrofit().getStudentService().delete(student.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                StudentDao dao = new StudentDao(context);
                dao.delete(student);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}