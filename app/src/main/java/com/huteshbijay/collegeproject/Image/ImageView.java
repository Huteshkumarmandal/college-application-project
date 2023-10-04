package com.huteshbijay.collegeproject.Image;

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
public class ImageView extends Fragment {



    public ImageView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_view, container, false);

        // Find the WebView by its ID
        WebView webView = view.findViewById(R.id.webView);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the web page
        String url = "http://10.0.2.2/CollegeInformationSystem/Image/ViewImage.php";
        webView.loadUrl(url);

        return view;


    }
}