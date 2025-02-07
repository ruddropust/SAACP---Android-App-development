package com.example.saacpfinal.AdminNotice;

public class NoticeModel {
    String title,message,fileurl;

    public NoticeModel() {
    }

    public NoticeModel(String title, String message, String fileurl) {
        this.title = title;
        this.message = message;
        this.fileurl = fileurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
