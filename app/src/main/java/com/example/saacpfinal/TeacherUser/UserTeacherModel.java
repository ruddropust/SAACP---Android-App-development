package com.example.saacpfinal.TeacherUser;

public class UserTeacherModel {
    String teachername, qualification, jobstatus, url;

    public UserTeacherModel() {
    }

    public UserTeacherModel(String teachername, String qualification, String jobstatus, String url) {
        this.teachername = teachername;
        this.qualification = qualification;
        this.jobstatus = jobstatus;
        this.url = url;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


