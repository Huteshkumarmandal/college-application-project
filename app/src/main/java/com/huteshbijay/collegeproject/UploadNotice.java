package com.huteshbijay.collegeproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class UploadNotice extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText noticeTitleEditText;
    private TextInputEditText noticeDescEditText;
    private Spinner departmentSpinner;
    private Spinner semesterSpinner;
    private ImageView noticeImageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        noticeTitleEditText = findViewById(R.id.noticeTitle);
        noticeDescEditText = findViewById(R.id.noticeDesc);
        departmentSpinner = findViewById(R.id.noticedepartment);
        semesterSpinner = findViewById(R.id.noticesemester);
        noticeImageView = findViewById(R.id.noticeImageView);
        Button uploadNoticeBtn = findViewById(R.id.uploadNoticeBtn);

        // Set up the click listener for the image selector
        MaterialCardView addImageCard = findViewById(R.id.addimage);
        addImageCard.setOnClickListener(v -> openImageSelector());

        // Set up the click listener for the upload button
        uploadNoticeBtn.setOnClickListener(v -> uploadNotice());

    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(noticeImageView);
        }
    }

    private void uploadNotice() {
        String title2 = noticeTitleEditText.getText().toString().trim();
        String department2 = departmentSpinner.getSelectedItem().toString();
        String semester2 = semesterSpinner.getSelectedItem().toString();
        String description2 = noticeDescEditText.getText().toString().trim();

        if (TextUtils.isEmpty(title2) || TextUtils.isEmpty(description2) || TextUtils.isEmpty(department2) || TextUtils.isEmpty(semester2) || imageUri == null) {
            Toast.makeText(this, "Please fill all the fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create request body for text fields

        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title2);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description2);
        RequestBody departmentBody = RequestBody.create(MediaType.parse("text/plain"), department2);
        RequestBody semesterBody = RequestBody.create(MediaType.parse("text/plain"), semester2);

//         Create request body for image file
        MultipartBody.Part imagePart = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), IOUtils.toByteArray(inputStream));
            imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageBody);
        } catch (IOException e) {
            e.printStackTrace();
            //return;
        }

        // Create API service
      // Call<Notice> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).uploadNotice(title, description, department, semester,);



        // Make the network request using the API service
        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(InterfaceMethods.class).uploadNotice(title2, description2, department2, semester2,imagePart);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UploadNotice.this, "Notice uploaded successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadNotice.this, "Failed to upload notice.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {


               Toast.makeText(UploadNotice.this, "Network error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
