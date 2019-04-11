package com.paris.agenda.modelo;

import java.util.List;

public class Test {

    private String matter;
    private String data;
    private List themes;

    public Test(String matter, String data, List themes) {
        this.matter = matter;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return this.matter;
    }
}
