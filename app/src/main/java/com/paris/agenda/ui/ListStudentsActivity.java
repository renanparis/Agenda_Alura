package com.paris.agenda.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.paris.agenda.R;
import com.paris.agenda.SendStudentTask;
import com.paris.agenda.adapter.StudentAdapter;
import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.dto.StudentSync;
import com.paris.agenda.modelo.Student;
import com.paris.agenda.retrofit.InitializerRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStudentsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CALL_PHONE = 111;
    public static final int REQUEST_CODE_SMS = 100;
    private ListView listStudents;
    private SwipeRefreshLayout swipe;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lits_students);
        swipe = findViewById(R.id.swipe_list_students);
        checkPermission();
        final ListView listStudents = configClickList();
        configFab();
        registerForContextMenu(listStudents);
        loadServeList();
        swipe.setOnRefreshListener(() -> loadServeList());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_students, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_list_students_send:
                new SendStudentTask(this).execute();
                break;

            case R.id.menu_list_students_receiver:
                Intent goToTest = new Intent(this, TestActivity.class);
                startActivity(goToTest);
                break;

            case R.id.menu_item_students_map:
                Intent goToMap = new Intent(this, MapActivity.class);
                startActivity(goToMap);
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_SMS);

        }
    }

    private void configFab() {
        Button newStudent = findViewById(R.id.add_student);
        newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListStudentsActivity.this, FormActivity.class);
                startActivity(intent);

            }
        });
    }

    private ListView configClickList() {
        final ListView listStudents = findViewById(R.id.list_students);
        listStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                Student student = (Student) listStudents.getItemAtPosition(position);
                Intent goToForm = new Intent(ListStudentsActivity.this, FormActivity.class);
                goToForm.putExtra("student", student);
                startActivity(goToForm);

            }
        });
        return listStudents;
    }

    public void updateList() {

        StudentDao dao = new StudentDao(this);
        List<Student> students = dao.searchStudents();
        dao.close();
        listStudents = findViewById(R.id.list_students);

        StudentAdapter adapter = new StudentAdapter(ListStudentsActivity.this, students);

        listStudents.setAdapter(adapter);

    }

    protected void onResume() {
        super.onResume();
        updateList();

    }

    private void loadServeList() {
        Call<StudentSync> listStudent = new InitializerRetrofit().getStudentService().listStudent();
        listStudent.enqueue(new Callback<StudentSync>() {
            @Override
            public void onResponse(Call<StudentSync> call, Response<StudentSync> response) {
                StudentSync studentSync = response.body();
                StudentDao studentDao = new StudentDao(ListStudentsActivity.this);
                studentDao.synchronize(studentSync.getStudents());
                studentDao.close();
                updateList();
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<StudentSync> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
                swipe.setRefreshing(false);


            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        final Student student = getStudentPosition((AdapterView.AdapterContextMenuInfo) menuInfo);

        MenuItem callPhone = menu.add("Ligar");

        callPhone.setOnMenuItemClickListener(configButtonCallPhone(student));

        MenuItem sendSms = menu.add("Enviar SMS");
        configButtonSms(student, sendSms);

        MenuItem visitMap = menu.add("Visitar Mapa");
        configButtonMap(student, visitMap);

        final MenuItem visitWebsite = menu.add("Visitar Site");
        configButtonWebSite(student, visitWebsite);

        MenuItem delete = menu.add("Deletar");

        delete.setOnMenuItemClickListener(configButtonDelete(student));
    }

    private MenuItem.OnMenuItemClickListener configButtonDelete(final Student student) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Call<Void> call = new InitializerRetrofit().getStudentService().delete(student.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        StudentDao dao = new StudentDao(ListStudentsActivity.this);
                        dao.delete(student);
                        dao.close();
                        updateList();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListStudentsActivity.this,
                                "Não foi possível deletar o aluno", Toast.LENGTH_LONG).show();

                    }
                });


                return false;
            }
        };
    }

    private void configButtonWebSite(Student student, MenuItem visitWebsite) {
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = student.getSite();

        if (!site.startsWith("https://")) {
            site = "https://" + site;
        }
        intentSite.setData(Uri.parse(site));
        visitWebsite.setIntent(intentSite);
    }

    private void configButtonMap(Student student, MenuItem visitMap) {
        Intent intentMap = new Intent(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse("geo:0,0?q=" + student.getAddress()));
        visitMap.setIntent(intentMap);
    }

    private void configButtonSms(Student student, MenuItem sendSms) {
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + student.getPhone()));
        sendSms.setIntent(intentSms);
    }

    private MenuItem.OnMenuItemClickListener configButtonCallPhone(final Student student) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListStudentsActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListStudentsActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
                } else {
                    Intent intentCallPhone = new Intent(Intent.ACTION_CALL);
                    intentCallPhone.setData(Uri.parse("tel:" + student.getPhone()));
                    startActivity(intentCallPhone);
                }

                return false;
            }
        };
    }

    private Student getStudentPosition(AdapterView.AdapterContextMenuInfo menuInfo) {
        return (Student) listStudents.getItemAtPosition(menuInfo.position);
    }


}
