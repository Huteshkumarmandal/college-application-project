package com.huteshbijay.collegeproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PdfViewerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private List<PdfItem> pdfItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        //recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfItemList = new ArrayList<>();
        pdfAdapter = new PdfAdapter(this, pdfItemList);
        recyclerView.setAdapter(pdfAdapter);

        // Replace "your_php_script_url" with the URL of your PHP script that fetches PDF data from the MySQL database
        String phpUrl = "http//10.2.2/CollegeDataBase/fetchpdf.php";
        new PdfDataFetcher().execute(phpUrl);
    }

    private class PdfDataFetcher extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String result = "";

            try {
                URL phpUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) phpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                result = stringBuilder.toString();
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Replace "pdf_title_key" and "pdf_url_key" with the keys used in your PHP script
                    String pdfTitle = jsonObject.getString("pdf_title_key");
                    String pdfUrl = jsonObject.getString("pdf_url_key");

                    // Create a PdfItem object and add it to the pdfItemList
                    PdfItem pdfItem = new PdfItem();
                    pdfItem.setTitle(pdfTitle);
                    pdfItem.setUrl(pdfUrl);
                    pdfItemList.add(pdfItem);
                }

                pdfAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


