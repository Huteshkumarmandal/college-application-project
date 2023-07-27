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
}