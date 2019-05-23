package com.paris.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paris.agenda.R;
import com.paris.agenda.modelo.Student;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private final List<Student> students;
    private final Context context;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = students.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null) {

            view = inflater.inflate(R.layout.activity_list_item, parent, false);
        }

        TextView fieldName = view.findViewById(R.id.activity_lits_item_name);
        fieldName.setText(student.getNome());
        TextView fieldPhone = view.findViewById(R.id.activity_list_item_phone);
        fieldPhone.setText(student.getTelefone());

        TextView fieldAddress = view.findViewById(R.id.activity_list_item_address);
        if (fieldAddress!= null){
            fieldAddress.setText(student.getEndereco());
        }

        TextView fieldSite = view.findViewById(R.id.activity_list_item_site);
        if (fieldSite != null){
            fieldSite.setText(student.getSite());
        }

        ImageView fieldPhoto = view.findViewById(R.id.activity_list_item_image);
        String localSavePhoto = student.getPhoto();

        if (localSavePhoto != null) {

            Bitmap bitmap = BitmapFactory.decodeFile(localSavePhoto);
            Bitmap bitmapReduced = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            fieldPhoto.setImageBitmap(bitmapReduced);
            fieldPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }


}

