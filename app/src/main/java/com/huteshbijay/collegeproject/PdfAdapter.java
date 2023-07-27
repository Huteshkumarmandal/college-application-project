package com.huteshbijay.collegeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    private Context context;
    private List<PdfItem> pdfItems;

    public PdfAdapter(Context context, List<PdfItem> pdfItems) {
        this.context = context;
        this.pdfItems = pdfItems;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pdf, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        // Bind data to views in the item_pdf layout
        // For example, you can set the PDF title or other information
    }

    @Override
    public int getItemCount() {
        return pdfItems.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {
        // Define views in the item_pdf layout here
        // For example, you can define a TextView to display the PDF title

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views here
        }
    }
}
