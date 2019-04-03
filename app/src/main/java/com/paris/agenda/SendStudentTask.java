package com.paris.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.paris.agenda.com.paris.agenda.db.StudentDao;
import com.paris.agenda.json.StudentConvert;
import com.paris.agenda.modelo.Student;

import java.util.List;

public class SendStudentTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public SendStudentTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        StudentDao dao = new StudentDao(context);
        List<Student> students = dao.searchStudents();
        dao.close();
        StudentConvert convert = new StudentConvert();
        String json = convert.convertToJSON(students);

        WebClient client = new WebClient();
        String averegeGrade = client.post(json);

        return averegeGrade;
    }

    @Override
    protected void onPreExecute() {
       dialog = ProgressDialog.show(context,
                "Aguarde", "Enviando Aluno", true, true);
    }

    @Override
    protected void onPostExecute(String averageGrade) {
        dialog.dismiss();

        Toast.makeText(context, "", Toast.LENGTH_LONG).show();
    }
}
