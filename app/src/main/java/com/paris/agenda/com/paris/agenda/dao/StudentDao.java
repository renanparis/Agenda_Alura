package com.paris.agenda.com.paris.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.paris.agenda.modelo.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDao extends SQLiteOpenHelper {


       public StudentDao(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Students(id INTEGER PRIMARY KEY, name TEXT NOT NULL, address TEXT, phone TEXT, site TEXT, grade REAL, localPhoto TEXT); ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Students ADD COLUMN localPhoto TEXT";
                db.execSQL(sql);

        }

    }

    public void insertStudent(Student student) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getContentValues(student);

        db.insert("Students", null, data);

    }

    private ContentValues getContentValues(Student student) {
        ContentValues data = new ContentValues();
        data.put("name", student.getName());
        data.put("address", student.getAddress());
        data.put("phone", student.getPhone());
        data.put("site", student.getSite());
        data.put("grade", student.getGrade());
        data.put("localPhoto", student.getPhoto());
        return data;
    }

    public List<Student> searchStudents() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students ;", null);
        List<Student> students = new ArrayList<Student>();
        while (c.moveToNext()) {
            Student student = new Student();
            student.setId(c.getLong(c.getColumnIndex("id")));
            student.setName(c.getString(c.getColumnIndex("name")));
            student.setAddress(c.getString(c.getColumnIndex("address")));
            student.setPhone(c.getString(c.getColumnIndex("phone")));
            student.setSite(c.getString(c.getColumnIndex("site")));
            student.setGrade(c.getDouble(c.getColumnIndex("grade")));
            student.setPhoto(c.getString(c.getColumnIndex("localPhoto")));
            students.add(student);
        }
        c.close();
        return students;
    }

    public void delete(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(student.getId())};
        db.delete("Students", "id=?", params);

    }

    public void updateStudent(Student student) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues date = getContentValues(student);

        String[] params = {String.valueOf(student.getId())};
        db.update("Students", date, "id=?", params);

    }
}
