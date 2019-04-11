package com.paris.agenda.modelo;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {

    private String matter;
    private String date;
    private List themes;

    public Test(String matter, String date, List themes) {
        this.matter = matter;
        this.date = date;
        this.themes = themes;
    }

    public List getThemes() {
        return themes;
    }

    public void setThemes(List themes) {
        this.themes = themes;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return this.matter;
    }
}
