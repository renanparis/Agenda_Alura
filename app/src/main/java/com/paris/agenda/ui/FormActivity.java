package com.paris.agenda.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paris.agenda.BuildConfig;
import com.paris.agenda.FormData;
import com.paris.agenda.R;
import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.modelo.Student;
import com.paris.agenda.retrofit.InitializerRetrofit;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_IMAGE_CAMERA = 111;
    private FormData formData;
    private String localSavePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomr);

        loadDataStudents();

        configButtonCamera();


    }

    private void loadDataStudents() {
        this.formData = new FormData(this);

        final Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("student");

        if (student != null) {
            formData.fillOutForm(student);
        }
    }

    private void configButtonCamera() {
        Button buttonCamera = findViewById(R.id.form_button_camera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                localSavePhoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File filePhoto = new File(localSavePhoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(FormActivity.this, BuildConfig.APPLICATION_ID + ".provider", filePhoto));
                startActivityForResult(intentCamera, REQUEST_CODE_IMAGE_CAMERA);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_CAMERA) {
            formData.loadPhoto(localSavePhoto);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                Student student = formData.getStudent();
                StudentDao dao = new StudentDao(this);

                student.notSynced();

                if (student.getId() != null) {
                    dao.updateStudent(student);
                } else {
                    dao.insertStudent(student);
                }

                dao.close();


                Call<Void> call = new InitializerRetrofit().getStudentService().insert(student);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("Response", "OK");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Faluire", "Erro");
                    }
                });

                Toast.makeText(FormActivity.this, "Aluno " + student.getNameStudent() + " Salvo com sucesso",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}


