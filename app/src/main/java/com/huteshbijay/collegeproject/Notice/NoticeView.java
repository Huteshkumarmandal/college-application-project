package com.huteshbijay.collegeproject.Notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.huteshbijay.collegeproject.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NoticeView extends Fragment {


        public NoticeView() {
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
           // String url = "https://hdccollegeproject.000webhostapp.com/AllNoticeView.php";
            String url = "http://CollegeInformationSystem/Notice/AllNoticeView.php";
            webView.loadUrl(url);

            return view;
        }
    }
