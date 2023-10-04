package com.huteshbijay.collegeproject.ViewNotice;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huteshbijay.collegeproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeViewAdapter extends RecyclerView.Adapter<NoticeViewAdapter.ViewHolder> {
    Activity context;
    ArrayList<NoticeViewDataModel>data;


    public NoticeViewAdapter(Activity context, ArrayList<NoticeViewDataModel>data){

        this.context=context;
        this.data=data;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.noticerecclerview_items, parent, false);
        return new ViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final  NoticeViewDataModel current=data.get(position);

//        holder.id.setText(current.getTitle()+"");
//        holder.title.setText(current.getTitle()+"");
//        holder.department.setText(current.getTitle()+"");
//        holder.semester.setText(current.getTitle()+"");
//       holder.description.setText(current.getTitle()+"");
//       holder.image_url.set
//       holder.date.setText(current.getTitle()+"");


        holder.id.setText(String.valueOf(current.getId()));
        holder.title.setText(current.getTitle());
        holder.department.setText(current.getDepartment());
        holder.semester.setText(current.getSemester());
        holder.description.setText(current.getDescription());
        holder.date.setText(current.getDate());

        // Load an image from a URL using Picasso
        Picasso.get()
                .load(current.getImageUrl())
                .placeholder(R.drawable.image_24) // Set a placeholder image
                .into(holder.image_url);

}

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, title, department, semester, description, date;
        ImageView image_url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            title =itemView.findViewById(R.id.title);
            department =itemView.findViewById(R.id.department);
            semester =itemView.findViewById(R.id.semester);
           description =itemView.findViewById(R.id.description);
            image_url =itemView.findViewById(R.id.image_url);

            date =itemView.findViewById(R.id.date);
            image_url = itemView.findViewById(R.id.image_url);


        }
    }
}
