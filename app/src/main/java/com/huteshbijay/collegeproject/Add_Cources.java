package com.huteshbijay.collegeproject;

import static com.huteshbijay.collegeproject.R.id.cource_code;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Cources extends AppCompatActivity {
    EditText cource_code, cource_name,total_sem_add;
    Spinner semester_add;
    Button button;
    TextView profile;
    LinearLayout error;
    ProgressDialog progressDialog;
    String SHARED_PREF = "myfref_xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cources);


        cource_code = findViewById(R.id.cource_code);
        cource_name = findViewById(R.id.cource_name);
        semester_add = findViewById(R.id.semester_add);
        total_sem_add = findViewById(R.id.total_sem_add);

        button = findViewById(R.id.add_cource);
        profile = findViewById(R.id.profileAdmin);
        error = findViewById(R.id.errorMainActivity);
        progressDialog = new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Admin.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cource_code.getText().toString().equals("")) {
                    cource_code.setError("required");
                } else if (semester_add.getSelectedItem().toString().equals("choose")) {
                    Toast.makeText(getApplicationContext(), "choose department", Toast.LENGTH_SHORT).show();
                } else if (cource_name.getText().toString().equals("")) {
                    cource_name.setError("required");
                } else {
                    addCource();
                    progressDialog.setTitle("please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
            }
        });
    }

    void addCource() {
        String cource_code2 = cource_code.getText().toString();
        String cource_name2 = cource_name.getText().toString();
        String semester_add2 = semester_add.getSelectedItem().toString();
        String total_sem_add2 = total_sem_add.getText().toString();


        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).addCource(cource_code2, cource_name2,semester_add2,total_sem_add2);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            Toast.makeText(getApplicationContext(), "record added !", Toast.LENGTH_LONG).show();
                            //customToast("record added !");
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "the user has already exist email is: " + response.body().getUserExist(), Toast.LENGTH_LONG).show();
                            //customToast("the user has already exist user ID is: "+response.body().getUserExist());
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    //is not 200
                }
            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {

            }
        });
    }

    void customToast(String putMessage) {
        Snackbar.make(error, putMessage, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}