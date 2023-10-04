package com.huteshbijay.collegeproject.Notice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.huteshbijay.collegeproject.R;


public class ViewNotice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);


        // Add the PdfFragment to the container
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new NoticeView())
                    .commit();
        }
    }
}