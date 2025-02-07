package com.example.saacpfinal.Authentication;

public class UserModel {
    private String emails,name,username,studentid,gender="",url,facebookurl,giturl,linkedinurl,location,bloodgroup;

    public UserModel() {
    }

    public UserModel(String emails, String name, String username, String studentid, String gender, String url, String facebookurl, String giturl, String linkedinurl, String location, String bloodgroup) {
        this.emails = emails;
        this.name = name;
        this.username = username;
        this.studentid = studentid;
        this.gender = ""+gender;
        this.url = url;
        this.facebookurl = facebookurl;
        this.giturl = giturl;
        this.linkedinurl = linkedinurl;
        this.location = location;
        this.bloodgroup = bloodgroup;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFacebookurl() {
        return facebookurl;
    }

    public void setFacebookurl(String facebookurl) {
        this.facebookurl = facebookurl;
    }

    public String getGiturl() {
        return giturl;
    }

    public void setGiturl(String giturl) {
        this.giturl = giturl;
    }

    public String getLinkedinurl() {
        return linkedinurl;
    }

    public void setLinkedinurl(String linkedinurl) {
        this.linkedinurl = linkedinurl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }
}
