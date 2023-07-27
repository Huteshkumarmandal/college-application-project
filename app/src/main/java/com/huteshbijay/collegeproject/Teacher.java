package com.huteshbijay.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Teacher extends AppCompatActivity {

    TextView role, email, department, date;
    String SHARED_PREF = "myfref_xml";
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        role = findViewById(R.id.rolein);
        email = findViewById(R.id.emailin);
        department = findViewById(R.id.depin);
        logout = findViewById(R.id.logoutin);
        date = findViewById(R.id.joinedin);

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

        role.setText("Role: " + sharedPreferences.getString("role", "user is not registered"));
        email.setText("Address: " + sharedPreferences.getString("email", "user is not registered"));
        department.setText("Department: " + sharedPreferences.getString("department", "user is not registered"));
        date.setText("Registered: " + sharedPreferences.getString("date", "user is not registered"));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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