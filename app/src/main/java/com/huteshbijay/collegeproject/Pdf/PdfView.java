package com.huteshbijay.collegeproject.Pdf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.huteshbijay.collegeproject.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PdfView extends Fragment {


    public PdfView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pdf_view, container, false);

        // Find the WebView by its ID
        WebView webView = view.findViewById(R.id.webView);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the web page
        String url = "https://hdccollegeproject.000webhostapp.com/ViewPdf.php";
        webView.loadUrl(url);

        return view;
    }
}