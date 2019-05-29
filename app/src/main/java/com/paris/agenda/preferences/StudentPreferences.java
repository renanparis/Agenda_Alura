package com.paris.agenda.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentPreferences {


    public static final String STUDENT_PREFERENCES = "com.paris.agenda.preferences.StudentPreferences";
    public static final String DATA_VERSION = "data_version";
    private Context context;

    public StudentPreferences(Context context) {
        this.context = context;
    }

    public void saveVersion(String version) {

        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DATA_VERSION, version);
        editor.commit();

    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(STUDENT_PREFERENCES, context.MODE_PRIVATE);
    }

    public String getVersion() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(DATA_VERSION, "");

    }

    public boolean hasVersion() {
        return !getVersion().isEmpty();
    }

    public boolean hasNewVersion(String version) {
        if (!hasVersion()) return true;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
            try {
                Date externalDate = format.parse(version);
                String internalVersion = getVersion();
                Date internalDate = format.parse(internalVersion);
                return externalDate.after(internalDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return false;
    }
}
