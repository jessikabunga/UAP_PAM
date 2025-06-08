package com.example.uap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uap.model.PlantItem;
import com.example.uap.R;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    private List<PlantItem> plantList;
    private Context context;

    public PlantAdapter(List<PlantItem> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.ViewHolder holder, int position) {
        PlantItem plant = plantList.get(position);
        holder.tvPlantName.setText(plant.getPlantName());
        holder.tvPrice.setText("Rp " + plant.getPrice());

        holder.ivPlantImage.setImageResource(R.drawable.image_card); // Gambar statis
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlantImage;
        TextView tvPlantName, tvPrice;
        Button btnDelete, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlantImage = itemView.findViewById(R.id.ivPlantImage);
            tvPlantName = itemView.findViewById(R.id.tvPlantName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}

