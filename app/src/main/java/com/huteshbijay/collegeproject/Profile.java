package com.huteshbijay.collegeproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Fragment {

    TextView currentUserTextView;


    TextView role, email, department, date;

    ImageView logout;


    String SHARED_PREF = "myfref_xml";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        role = view.findViewById(R.id.roleadmin);
        email = view.findViewById(R.id.emailadmin);
        department = view.findViewById(R.id.depadmin);
        logout = view.findViewById(R.id.logoutadmin);
        date = view.findViewById(R.id.joinedadmin);



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        String currentUser = sharedPreferences.getString("email", null);
        //if user does not login, then redirect to login page
        if (currentUser == null ||currentUser.equals(""))
        {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().finish();
            Toast.makeText(getContext(), "you logged out", Toast.LENGTH_LONG).show();
        }else {
            if (currentUserTextView != null) {
                currentUserTextView.setText(currentUser);



        role.setText("Role: " + sharedPreferences.getString("role", "user is not registered"));
        date.setText("Registered: " + sharedPreferences.getString("date", "user is not registered"));
        email.setText("Address: " + sharedPreferences.getString("email", "user is not registered"));
        department.setText("Department: " + sharedPreferences.getString("department", "user is not registered"));

            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                //finish();
            }
        });
        return view;

    }
}