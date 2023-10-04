package com.huteshbijay.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button button;
    ProgressDialog progressDialog;
    String SHARED_PREF = "myfref_xml";

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //now, initialize variable
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        textView = findViewById(R.id.aboutuswithoutlogin); // Your TextView

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutUs.class);

                startActivity(intent);

            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //here, check whether user opens App at the second time, no login is required
        //mean that, if shared preference is not null, then there is a value saved last login
        //session jastae ho yo

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String role = sharedPreferences.getString("role", null);


        if (email != null) {
            switch (role) {
                case "student":
                    Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                    break;
                case "admin":
                    Intent intent2 = new Intent(getApplicationContext(), Admin_Dashboard.class);
                    startActivity(intent2);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                    break;
                case "teacher":
                    Intent intent3 = new Intent(getApplicationContext(), TeacherDashboard.class);
                    startActivity(intent3);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        //here , when login button is clicked  validate the form
        //if all fields are filled then we call 'login' method

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("")) {
                    username.setError("username name is required");
                } else if (password.getText().toString().equals("")) {
                    password.setError("password name is required");
                } else {
                    login();
                    progressDialog.setTitle("please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    // progressDialog.setCancelable(false);
                    progressDialog.show();

                }

            }
        });


    }

    //login method

    void login() {

        String email2 = username.getText().toString();
        String password2 = password.getText().toString();



        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).loginMethod(email2, password2);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("email", response.body().getEmail());
                            editor.putString("role", response.body().getRole());
                            editor.putString("department", response.body().getDepartment());
                            editor.putString("password", response.body().getPassword());
                            editor.putString("date", response.body().getDate());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_LONG).show();
                            String role = response.body().getRole();


                            switch (role) {
                                case "student":
                                    Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
                                    startActivity(intent);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    progressDialog.dismiss();
                                    break;

                                case "admin":
                                    Intent intent2 = new Intent(getApplicationContext(), Admin_Dashboard.class);
                                    startActivity(intent2);
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    progressDialog.dismiss();
                                    break;

                                case "teacher":
                                    Intent intent3 = new Intent(getApplicationContext(), TeacherDashboard.class);
                                    startActivity(intent3);
                                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    progressDialog.dismiss();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "invalid username or password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    //no connection
                    //connection
                }

            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {


            }
        });
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}