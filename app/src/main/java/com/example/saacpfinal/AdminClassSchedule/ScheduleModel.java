package com.example.saacpfinal.AdminClassSchedule;

public class ScheduleModel {
    String courseName,courseTeacher,date,time;

    public ScheduleModel() {
    }

    public ScheduleModel(String courseName, String courseTeacher, String date, String time) {
        this.courseName = courseName;
        this.courseTeacher = courseTeacher;
        this.date = date;
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
