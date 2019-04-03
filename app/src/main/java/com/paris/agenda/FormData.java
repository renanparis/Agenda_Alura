package com.paris.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.paris.agenda.modelo.Student;
import com.paris.agenda.ui.FormActivity;

public class FormData {

    private EditText fieldName;
    private EditText fieldAddress;
    private EditText fieldPhone;
    private EditText fieldSite;
    private RatingBar fieldGrade;
    private ImageView fieldPhoto;
    private Student student;

    public FormData(FormActivity activity) {
        this.fieldName = activity.findViewById(R.id.form_name);
        this.fieldAddress = activity.findViewById(R.id.form_adress);
        this.fieldPhone = activity.findViewById(R.id.form_phone);
        this.fieldSite = activity.findViewById(R.id.form_site);
        this.fieldGrade = activity.findViewById(R.id.form_grade);
        this.fieldPhoto = activity.findViewById(R.id.form_image);
        student = new Student();

    }

    public Student getStudent() {

        student.setName(fieldName.getText().toString());
        student.setAddress(fieldAddress.getText().toString());
        student.setPhone(fieldPhone.getText().toString());
        student.setSite(fieldSite.getText().toString());
        student.setGrade(Double.valueOf(fieldGrade.getProgress()));
        student.setPhoto((String) fieldPhoto.getTag());

        return student;
    }

    public void fillOutForm(Student student) {

        fieldName.setText(student.getName());
        fieldAddress.setText(student.getAddress());
        fieldPhone.setText(student.getPhone());
        fieldSite.setText(student.getSite());
        fieldGrade.setProgress(student.getGrade().intValue());

        if (student.getPhoto() != null) {
            loadPhoto(student.getPhoto());
        }
        this.student = student;
    }

    public void loadPhoto(String localSavePhoto) {

        if (localSavePhoto != null) {

            Bitmap bitmap = BitmapFactory.decodeFile(localSavePhoto);
            Bitmap bitmapReduced = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            fieldPhoto.setImageBitmap(bitmapReduced);
            fieldPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            fieldPhoto.setTag(localSavePhoto);
        }

    }
}
