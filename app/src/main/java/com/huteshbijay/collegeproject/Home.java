package com.huteshbijay.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
public class Home extends Fragment {

    Button  noticeButton;

    Button  GalleryButton;

    Button PdfButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        noticeButton = view.findViewById(R.id.notice);
        GalleryButton  = view.findViewById(R.id.Gallery);
        PdfButton = view.findViewById(R.id.PDF);


        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadNotice.class);
                startActivity(intent);
            }
        });

        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadImage.class);
                startActivity(intent);
            }
        });

        PdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPdf.class);
                startActivity(intent);
            }
        });

        return view;

    }
}
