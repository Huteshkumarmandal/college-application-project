package com.huteshbijay.collegeproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionDatabase {

   // private static final String BASE_URL = "http://hdccollegeproject.000webhostapp.com/";
    private static final String BASE_URL = "http://192.168.121.233/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
