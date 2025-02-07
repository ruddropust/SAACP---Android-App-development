package com.example.saacpfinal.CGPACalculator;

public class item {
    String subjects,gread,crdit;


    public item(String subjects, String crdit, String gread) {
        this.subjects = subjects;
        this.gread = gread;
        this.crdit = crdit;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getGread() {
        return gread;
    }

    public String getCrdit() {
        return crdit;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public void setGread(String gread) {
        this.gread = gread;
    }

    public void setCrdit(String crdit) {
        this.crdit = crdit;
    }
}
