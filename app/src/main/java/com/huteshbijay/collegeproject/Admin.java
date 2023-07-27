package com.huteshbijay.collegeproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    TextView role, email, department, date;


    String SHARED_PREF = "myfref_xml";
    ImageView logout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        role = findViewById(R.id.roleadmin);
        email = findViewById(R.id.emailadmin);
        department = findViewById(R.id.depadmin);
        logout = findViewById(R.id.logoutadmin);
        date = findViewById(R.id.joinedadmin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String currentUser = sharedPreferences.getString("email", null);
        //if user does not login, then redirect to login page
        if (currentUser == null ||currentUser.equals(""))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            Toast.makeText(getApplicationContext(), "you logged out", Toast.LENGTH_LONG).show();
        }

        role.setText("Role: " + sharedPreferences.getString("role", "user is not registered"));
        date.setText("Registered: " + sharedPreferences.getString("date", "user is not registered"));
        email.setText("Address: " + sharedPreferences.getString("email", "user is not registered"));
        department.setText("Department: " + sharedPreferences.getString("department", "user is not registered"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}