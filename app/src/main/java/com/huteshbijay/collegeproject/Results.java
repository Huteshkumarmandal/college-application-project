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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Results extends AppCompatActivity {
    EditText management, comm, politics, field, project, regNo;
    Button button;
    Spinner semister;
    ProgressDialog progressDialog;
    TextView profile;
    String SHARED_PREF = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        management = findViewById(R.id.management);
        comm = findViewById(R.id.commskilss);
        politics = findViewById(R.id.politics);
        field = findViewById(R.id.field);
        project = findViewById(R.id.project);
        button = findViewById(R.id.upload);
        regNo = findViewById(R.id.regNo);
        semister = findViewById(R.id.semister);
        profile = findViewById(R.id.profileInstructor);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentUser = sharedPreferences.getString("email", null);
        //if user does not login, then redirect to login
        if (currentUser == null ||currentUser.equals(""))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            Toast.makeText(getApplicationContext(), "you logged out", Toast.LENGTH_LONG).show();
        }

        String password = sharedPreferences.getString("password", null);
        if (password.equals("123")) {
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(intent);
            finish();
        }


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Teacher.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regNo.getText().toString().equals("")) {
                    regNo.setText("required");
                } else {
                    progressDialog.setTitle("please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    addResults();
                }
            }
        });
    }

    void addResults() {
        String management2 = management.getText().toString();
        String politics2 = politics.getText().toString();
        String project2 = project.getText().toString();
        String field2 = field.getText().toString();
        String comm2 = comm.getText().toString();
        String regno2 = regNo.getText().toString();
        String semister2 = semister.getSelectedItem().toString();

        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).addResults(management2, politics2, project2, field2, comm2, semister2, regno2);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            Toast.makeText(getApplicationContext(), "you have successfull add results", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            button.setText("successfull add record");
                        } else if (response.body().getResultCode() == 0) {
                            Toast.makeText(getApplicationContext(), "the student does not exist", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            button.setText("retry...");
                        } else if (response.body().getResultCode() == 2) {
                            Toast.makeText(getApplicationContext(), "the semister you have tried to upload has already uploaded", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            button.setText("retry...");
                        }
                    }
                } else {
                    //not 200
                }
            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}