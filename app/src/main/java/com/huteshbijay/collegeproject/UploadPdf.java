package com.huteshbijay.collegeproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadPdf extends AppCompatActivity {

    private CardView addpdf;
    private EditText pdfTitle;
    private Button uploadpdfBtn;
    private TextView pdfTextView;

    private String pdfName, title;
    private final int REQ = 1;
    private Uri pdfData;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading pdf");


        addpdf = findViewById(R.id.addpdf);
        pdfTitle = findViewById(R.id.pdfTitle);
        uploadpdfBtn = findViewById(R.id.uploadpdfBtn);
        pdfTextView = findViewById(R.id.pdfTextView);

        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        uploadpdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()) {
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                } else if (pdfData == null) {
                    Toast.makeText(UploadPdf.this, "Please upload pdf", Toast.LENGTH_SHORT).show();
                } else {
                    pd.setTitle("Please wait..");
                    pd.setMessage("Uploading pdf");
                    pd.show();
                    uploadPdfData();
                }
            }
        });
    }

    private void uploadPdfData() {


        RequestQueue queue = Volley.newRequestQueue(this);

        // URL to your PHP script on the local server (change it according to your setup)
        String url = "http://10.0.2.2/CollegeDataBase/upload_pdf.php";

        // Create a Volley RequestQueue

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                Toast.makeText(UploadPdf.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                pdfTitle.setText("");
                                pdfTextView.setText("");
                            } else {
                                Toast.makeText(UploadPdf.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UploadPdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred while sending the data
                        pd.dismiss();
                        Toast.makeText(UploadPdf.this, "Error uploading pdf", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Pass the title and pdf data (URL) to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("pdf_file", pdfData.toString());
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(request);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pdf file"), REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadPdf.this.getContentResolver().query(pdfData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }

            pdfTextView.setText(pdfName);
        }
    }
}
