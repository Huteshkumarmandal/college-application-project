package com.huteshbijay.collegeproject.AddStudent;



import android.annotation.SuppressLint;
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

public class AddStudent extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText studentname, fathername;
    private TextInputEditText contact, email, parentcontactnumber, turegno, DOB, pardesno, city;
    private Spinner gender;
    private Spinner department;
    private Spinner semester;


    private CardView selectImage;
    private Button AddStudentBtn;
    private ImageView studentimageview;

    private String departmentstring, semesterstring, genderstring;

    private final int REQ = 1;
    private Bitmap bitmap;
    ProgressDialog pd;
    private CharSequence title;
    private CharSequence content;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        selectImage = findViewById(R.id.addimage);
        studentname = findViewById(R.id.studentname);
        fathername = findViewById(R.id.fathername);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        parentcontactnumber = findViewById(R.id.parentcontactnumber);
        turegno = findViewById(R.id.turegno);
        DOB = findViewById(R.id.DOB);
        pardesno = findViewById(R.id.pardeshno);
        city = findViewById(R.id.city);

        gender = findViewById(R.id.gender);
        department = findViewById(R.id.department);
        semester = findViewById(R.id.semester);
        AddStudentBtn = findViewById(R.id.AddStudentBtn);
        studentimageview = findViewById(R.id.studentImageView);

        pd = new ProgressDialog(this);

        String[] genderItems = new String[]{"Gender", "Male", "Female"};
        String[] departmentItems = new String[]{"Select Department", "BCA", "BIM", "BSCIT", "BBS", "General"};
        String[] semesterItems = new String[]{"Select Semester", "semester 1", "semester 2", "semester 3", "semester 4", "semester 5", "semester 6", "semester 7", "semester 8", "year 1", "year 2", "year 3", "year 4"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderItems);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departmentItems);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semesterItems);

        department.setAdapter(departmentAdapter);
        semester.setAdapter(semesterAdapter);
        gender.setAdapter(genderAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderstring = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departmentstring = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        AddStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap == null) {
                    Toast.makeText(AddStudent.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                } else if (departmentstring.equals("Select Category")) {
                    Toast.makeText(AddStudent.this, "Please select an image category", Toast.LENGTH_SHORT).show();
                } else if (studentname.getText().toString().equals("")) {
                    studentname.setError("required");

                } else if (fathername.getText().toString().equals("")) {
                    fathername.setError("required");

                } else if (contact.getText().toString().equals("")) {
                    contact.setError("required");

                } else if (email.getText().toString().equals("")) {
                    email.setError("required");

                } else if (parentcontactnumber.getText().toString().equals("")) {
                    fathername.setError("required");

                } else if (turegno.getText().toString().equals("")) {
                    turegno.setError("required");

                } else if (DOB.getText().toString().equals("")) {
                    DOB.setError("required");

                } else if (pardesno.getText().toString().equals("")) {
                    pardesno.setError("required");

                } else if (city.getText().toString().equals("")) {
                    city.setError("required");

                } else {
                    pd.setMessage("Uploading...");
                    pd.show();
                    uploadNoticeBtn();

                }
            }
        });

    }

    private void uploadNoticeBtn() {

        String student_name1 = studentname.getText().toString();
        String fathername1 = fathername.getText().toString();
        String contact1 = contact.getText().toString();
        String email1 = email.getText().toString();
        String parentcontactno1 = parentcontactnumber.getText().toString();
        String turegno1 = turegno.getText().toString();
        String Dob1 = DOB.getText().toString();
        String pardesno1 = pardesno.getText().toString();
        String city1 = city.getText().toString();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();

        // URL to your PHP script on the local server (change it according to your setup)

        //URL for 000websobhost
        // String url = "https://hdccollegeproject.000webhostapp.com/addStudent.php";
        String url = "http://10.0.2.2/CollegeDataBase/addStudent.php";


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        pd.dismiss();
                        Toast.makeText(AddStudent.this, response, Toast.LENGTH_SHORT).show();

                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred while sending the data
                        pd.dismiss();
                        Toast.makeText(AddStudent.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Convert the bitmap to a Base64 string
                String imageData = Base64.encodeToString(finalImg, Base64.DEFAULT);

                // Pass the category and image data to the PHP script
                Map<String, String> params = new HashMap<>();

                params.put("studentname", student_name1);
                params.put("fathername", fathername1);
                params.put("contact", contact1);
                params.put("email", email1);
                params.put("parentcontactno", parentcontactno1);
                params.put("turegno", turegno1);
                params.put("DOB", Dob1);
                params.put("pardesno", pardesno1);
                params.put("city", city1);

                params.put("genderstring", genderstring);
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
        startActivityForResult(pickImage, REQ);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
            studentimageview.setImageBitmap(bitmap);
        }
    }
}