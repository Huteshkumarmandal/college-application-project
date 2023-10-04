package com.huteshbijay.collegeproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.huteshbijay.collegeproject.Image.ImageView;
import com.huteshbijay.collegeproject.Notice.NoticeView;
import com.huteshbijay.collegeproject.Pdf.PdfView;
import com.huteshbijay.collegeproject.Pdf.ViewPdf;


public class Admin_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String SHARED_PREF = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        editor = sharedPreferences.edit();

        // Now, check if the editor is not null before calling clear()

        String currentUser = sharedPreferences.getString("email", null);
        //if user does not login, then redirect to login page
        if (currentUser == null || currentUser.equals("")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            Toast.makeText(getApplicationContext(), "you logged out", Toast.LENGTH_LONG).show();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        } else if (itemId == R.id.Notice) {

//            Intent intent = new Intent(getApplicationContext(), ViewNotice.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoticeView()).commit();
        } else if (itemId == R.id.Pdf) {

//            Intent intent = new Intent(getApplicationContext(), ViewPdf.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PdfView()).commit();
        } else if (itemId == R.id.Gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImageView()).commit();
        } else if (itemId == R.id.Users) {

            Intent intent = new Intent(getApplicationContext(), Admin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
          //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin()).commit();
        }


        else if (itemId == R.id.addusers) {

           // editor.remove("email").apply();
            //  sharedPreferences.edit().remove("email").apply();

            Intent intent = new Intent(getApplicationContext(), Adduser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

        else if (itemId == R.id.logout) {

            editor.remove("email").apply();
           //  sharedPreferences.edit().remove("email").apply();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}