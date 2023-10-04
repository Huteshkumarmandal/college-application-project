package com.huteshbijay.collegeproject.Pdf;


import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.huteshbijay.collegeproject.R;


public class ViewPdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpdfweb);

        // Find the WebView by its ID
        WebView webView = findViewById(R.id.webView);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the web page
       // String url = "https://hdccollegeproject.000webhostapp.com/ViewPdf.php";
        String url = "http://10.0.2.2/CollegeInformationSystem/PDF/ViewPdf.php";
        webView.loadUrl(url);


    }
}