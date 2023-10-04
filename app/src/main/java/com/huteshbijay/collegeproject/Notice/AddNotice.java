package com.huteshbijay.collegeproject.Notice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.huteshbijay.collegeproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddNotice extends AppCompatActivity {

   // private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText notice_title,noticeDesc;
    private Spinner notice_department;
    private Spinner notice_semester;
    private CardView selectImage;
    private Button uploadNoticeBtn;
    private ImageView noticeImageView;

    private String departmentstring,semesterstring;

    private final int REQ = 1;
    private Bitmap bitmap;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        selectImage = findViewById(R.id.addimage);
        notice_title = findViewById(R.id.noticeTitle);
        noticeDesc = findViewById(R.id.noticeDesc);
        notice_department = findViewById(R.id.noticedepartment);
        notice_semester = findViewById(R.id.noticesemester);
        uploadNoticeBtn = findViewById(R.id.uploadNoticeBtn);
        noticeImageView = findViewById(R.id.noticeImageView);

        pd = new ProgressDialog(this);

        String[] departmentItems = new String[]{"Select Department", "BCA", "BIM", "BSCIT", "BBS", "General"};
        String[] semesterItems = new String[]{"Select Semester", "semester 1", "semester 2", "semester 3", "semester 4", "semester 5", "semester 6", "semester 7", "semester 8", "year 1", "year 2", "year 3", "year 4"};

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departmentItems);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semesterItems);

        notice_department.setAdapter(departmentAdapter);
        notice_semester.setAdapter(semesterAdapter);

        notice_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departmentstring = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notice_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semesterstring = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (bitmap == null) {
                    Toast.makeText(AddNotice.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                } else if (departmentstring.equals("Select Category")) {
                    Toast.makeText(AddNotice.this, "Please select an image category", Toast.LENGTH_SHORT).show();
                } else if (notice_title.getText().toString().equals("")){
                    notice_title.setError("required");



                } else if (noticeDesc.getText().toString().equals("")) {
                    noticeDesc.setError("required");
                    
                }else {
                    pd.setMessage("Uploading...");
                    pd.show();
                    uploadNoticeBtn();
                }
            }
        });

    }

    private void uploadNoticeBtn(){


        String  notice_title1= notice_title.getText().toString();
        String noticeDesc1= noticeDesc.getText().toString();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();

        // URL to your PHP script on the local server (change it according to your setup)
        String url = "http://10.0.2.2/CollegeDataBase/uploadNotice.php";

        //URL for 000websobhost
        //String url = "https://hdccollegeproject.000webhostapp.com/uploadNotice.php";



        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        pd.dismiss();
                        Toast.makeText(AddNotice.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred while sending the data
                        pd.dismiss();
                        Toast.makeText(AddNotice.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Convert the bitmap to a Base64 string
                String imageData = Base64.encodeToString(finalImg, Base64.DEFAULT);

                // Pass the category and image data to the PHP script
                Map<String, String> params = new HashMap<>();

                params.put("notice_title", notice_title1);
                params.put("noticeDesc", noticeDesc1);
                params.put("departmentstring", departmentstring);
                params.put("semesterstring", semesterstring);

                params.put("image_data", imageData);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void openGallery() {

        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
            noticeImageView.setImageBitmap(bitmap);
        }
    }
}