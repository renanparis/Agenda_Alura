package com.paris.agenda.com.paris.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.paris.agenda.modelo.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDao extends SQLiteOpenHelper {


    public StudentDao(Context context) {
        super(context, "Agenda", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Students(id CHAR(36) PRIMARY KEY, name TEXT NOT NULL, address TEXT, phone TEXT, site TEXT, grade REAL, localPhoto TEXT); ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Students ADD COLUMN localPhoto TEXT";
                db.execSQL(sql);
            case 2:
                String createNewTable = "CREATE TABLE New_students " +
                        "(id CHAR(36) PRIMARY KEY," +
                        " name TEXT NOT NULL, " +
                        "address TEXT, phone TEXT, " +
                        "site TEXT, grade REAL, localPhoto TEXT);";
                db.execSQL(createNewTable);

                String insertsDataNewTable = "INSERT INTO New_students " +
                        "(id, name, address, phone, site, grade, localPhoto) " +
                        "SELECT id, name, address, phone, site, grade, localPhoto " +
                        "FROM Students";
                db.execSQL(insertsDataNewTable);

                String removeOldTable = "DROP TABLE Students";
                db.execSQL(removeOldTable);

                String renameNewTable = "ALTER TABLE New_students " +
                        "RENAME TO Students";
                db.execSQL(renameNewTable);

            case 3:
                String searchStudents = "SELECT * FROM Students";
                Cursor cursor = db.rawQuery(searchStudents, null);
                List<Student> students = fillStudents(cursor);
                String updateIdStudent = "UPDATE Students SET id=? WHERE id=?";

                for (Student student:
                     students) {
                    db.execSQL(updateIdStudent, new String [] {createUUID(), student.getId()});

                }



        }

    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    public void insertStudent(Student student) {

        SQLiteDatabase db = getWritableDatabase();
        insertIdIfNecessary(student);
        ContentValues data = getContentValues(student);
        db.insert("Students", null, data);

    }

    private void insertIdIfNecessary(Student student) {
        if (student.getId() == null){
            student.setId(createUUID());
        }
    }

    private ContentValues getContentValues(Student student) {
        ContentValues data = new ContentValues();
        data.put("id", student.getId());
        data.put("name", student.getNameStudent());
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
        List<Student> students = fillStudents(c);
        return students;
    }

    private List<Student> fillStudents(Cursor c) {
        List<Student> students = new ArrayList<Student>();
        while (c.moveToNext()) {
            Student student = new Student();
            student.setId(c.getString(c.getColumnIndex("id")));
            student.setNameStudent(c.getString(c.getColumnIndex("name")));
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

    public boolean isStudent(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students WHERE phone = ?", new String[]{phone});
        int result = c.getCount();
        c.close();
        return result > 0;

    }


    public void synchronize(List<Student> students) {
        Log.i("aluno", String.valueOf(students));
        for (Student student:
             students) {
            if (exist(student)){
                if (student.isDesabled()){
                    delete(student);

                }else {
                    updateStudent(student);

                }
            }else if (!student.isDesabled()){
                insertStudent(student);
            }
        }

    }

    private boolean exist(Student student) {
        SQLiteDatabase db = getReadableDatabase();
        String existId = "SELECT id FROM Students WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existId, new String[]{student.getId()});
        int count = cursor.getCount();
        return count > 0;
    }
}
