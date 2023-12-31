package com.huteshbijay.collegeproject;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface InterfaceMethods {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseFormServer> loginMethod(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("addUser.php")
    Call<ResponseFormServer> addUsers(@Field("firstname") String firstname, @Field("lastname") String lastname, @Field("email") String email, @Field("department") String department, @Field("role") String role);

    @FormUrlEncoded
    @POST("addCource.php")
    Call<ResponseFormServer> addCource(@Field("cource_code") String cource_code, @Field("cource_name") String cource_name,  @Field("semester_add") String semester_add, @Field("total_sem_add") String total_sem_add);

    @FormUrlEncoded
    @POST("Results.php")
    Call<ResponseFormServer> addResults(@Field("management") String management,
                                        @Field("politics") String politics,
                                        @Field("project") String project,
                                        @Field("field") String field,
                                        @Field("commskills") String commskills,
                                        @Field("semister") String semister,
                                        @Field("regNo") String regNo);


    @FormUrlEncoded

    @POST("updatePassword.php")

    Call<ResponseFormServer> updatePasswrd(@Field("oldpassword") String oldpassword, @Field("newpassword") String newpassword, @Field("email") String email);


    @Multipart
    @POST("uploadNotice.php")
    Call<ResponseFormServer> uploadNotice(@Part("title") String title,
                              @Part("department") String department,
                              @Part("semester") String semester,
                              @Part("description") String description,
                              @Part MultipartBody.Part imagepart);

//    Call<Notice> uploadNotice(RequestBody titleBody, RequestBody descriptionBody, RequestBody departmentBody, RequestBody semesterBody, MultipartBody.Part imagePart);



}

