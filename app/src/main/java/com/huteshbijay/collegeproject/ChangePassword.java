package com.huteshbijay.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    EditText oldpass, newpass, confirmpass;
    Button button;
    String SHARED_PREF = "myfref_xml";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldpass = findViewById(R.id.oldpassword);
        newpass = findViewById(R.id.newpassword);
        confirmpass = findViewById(R.id.confirmpassword);
        button = findViewById(R.id.changepass);
        progressDialog = new ProgressDialog(this);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newpass.getText().toString().equals("")) {
                    newpass.setError("enter new password");
                } else if (oldpass.getText().toString().equals("")) {
                    oldpass.setError("enter old password");
                } else if (confirmpass.getText().toString().equals("")) {
                    confirmpass.setError("confirm password");
                } else if (!confirmpass.getText().toString().equals(newpass.getText().toString())) {
                    newpass.setError("enter new password");
                } else {
                    progressDialog.setTitle("please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    // progressDialog.setCancelable(false);
                    progressDialog.show();
                    changePassword();
                }
            }
        });
    }

    void changePassword() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String oldpass2 = oldpass.getText().toString();
        String newpass2 = newpass.getText().toString();
        String email = sharedPreferences.getString("email", "null");
        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).updatePasswrd(oldpass2, newpass2, email);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            Toast.makeText(getApplicationContext(), "you have successfully update the password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            button.setText("Go to Dashboard");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    editor.clear();
                                    editor.commit();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "old password does not exist on the database", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    //connection error
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