package com.huteshbijay.collegeproject;

import com.google.gson.annotations.SerializedName;

public class ResponseFormServer
{
    @SerializedName("status")
    String status;

    @SerializedName("resultCode")
    int resultCode;

    @SerializedName("role")
    String role;

    @SerializedName("email")
    String email;

    @SerializedName("department")
    String department;

    @SerializedName("userExist")
    String userExist;

    @SerializedName("password")
    String password;

    @SerializedName("date")
    String date;


//    //notice ko lagii
//    @SerializedName("title")
//    String title;
//
//    @SerializedName("description")
//    String description;
//
//    @SerializedName("department1")
//    String department1;
//
//    @SerializedName("semester")
//    String semester;
//
//    @SerializedName("imageUrl")
//    String imageUrl;


    public String getDate() {
        return date;
    }

    public  String getPassword()
    {
        return  password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserExist() {
        return userExist;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus()
    {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getRole() {
        return role;
    }


    //notice ko response

    @SerializedName("title")
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("department1")
    int department1;

    public int getDepartment1() {
        return department1;
    }

    public void setDepartment(int department) {
        this.department1 = department1;
    }

    @SerializedName("semester")
    String semester;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @SerializedName("description")
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("imagepart")
    String imagepart;

    public String getimagepart() {
        return getimagepart();
    }

    public void setImageUrl(String imagepart) {
        this.imagepart = imagepart;
    }


}
