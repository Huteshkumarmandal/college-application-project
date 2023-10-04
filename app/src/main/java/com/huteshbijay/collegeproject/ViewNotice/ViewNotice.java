package com.huteshbijay.collegeproject.ViewNotice;
// ViewNotice.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.huteshbijay.collegeproject.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ViewNotice extends AppCompatActivity {
    private NoticeViewAdapter recyclerAdapter;
    private ArrayList<NoticeViewDataModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        RecyclerView recyclerView = findViewById(R.id.noticerecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        recyclerAdapter = new NoticeViewAdapter(this, data);
        recyclerView.setAdapter(recyclerAdapter);

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        // Replace with your API URL
        String url = "http://10.0.2.2/CollegeDataBase/viewnotice.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    data.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("title");
                            String department = jsonObject.getString("department");
                            // Parse other fields as needed
                             String semester = jsonObject.getString("semester");
                             String description = jsonObject.getString("description");
                            String image_url = jsonObject.getString("image_url");

                            String date = jsonObject.getString("date");

                            // Create a data model object and add it to the list
                            NoticeViewDataModel item = new NoticeViewDataModel(id, title, department,semester,description,date,image_url);
                            data.add(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged();
                }, error -> {
                    // Handle errors here
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
