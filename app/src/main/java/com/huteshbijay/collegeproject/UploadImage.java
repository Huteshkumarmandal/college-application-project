package com.huteshbijay.collegeproject;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity {

    private Spinner imageCategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;

    private String category;
    private final int REQ = 1;
    private Bitmap bitmap;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        selectImage = findViewById(R.id.addGalleryImage);
        imageCategory = findViewById(R.id.image_category);
        uploadImage = findViewById(R.id.uploadImageBtn);
        galleryImageView = findViewById(R.id.GalleryImageView);

        pd = new ProgressDialog(this);

        String[] items = new String[]{"Select Category", "BCA", "BIM", "BSCIT", "BBS", "Others Events"};

        imageCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));

        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = imageCategory.getSelectedItem().toString();
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

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap == null) {
                    Toast.makeText(UploadImage.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                } else if (category.equals("Select Category")) {
                    Toast.makeText(UploadImage.this, "Please select an image category", Toast.LENGTH_SHORT).show();
                } else {
                    pd.setMessage("Uploading...");
                    pd.show();
                    uploadImage();
                }
            }
        });
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();

        // URL to your PHP script on the local server (change it according to your setup)
        String url = "http://10.0.2.2/CollegeDataBase/UploadGallery.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        pd.dismiss();
                        Toast.makeText(UploadImage.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred while sending the data
                        pd.dismiss();
                        Toast.makeText(UploadImage.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Convert the bitmap to a Base64 string
                String imageData = Base64.encodeToString(finalImg, Base64.DEFAULT);

                // Pass the category and image data to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("category", category);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            galleryImageView.setImageBitmap(bitmap);
        }
    }
}
