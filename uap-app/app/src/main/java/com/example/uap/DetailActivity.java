package com.example.uap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uap.model.PlantItem;
import com.example.uap.network.ApiClient;
import com.example.uap.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDescription;
    private Button btnUpdate;
    private String plantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDesc);
        btnUpdate = findViewById(R.id.btnUpdate);

        plantName = getIntent().getStringExtra("plant_name");

        if (plantName != null) {
            fetchPlantDetail(plantName);
        } else {
            Toast.makeText(this, "Nama tanaman tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
            intent.putExtra("plant_name", plantName);
            startActivity(intent);
        });
    }

    private void fetchPlantDetail(String name) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PlantItem> call = apiService.getPlantByName(name);
        call.enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(Call<PlantItem> call, Response<PlantItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlantItem plant = response.body();
                    tvName.setText(plant.getPlantName());
                    tvPrice.setText("Rp " + plant.getPrice());
                    tvDescription.setText(plant.getDescription());
                } else {
                    Toast.makeText(DetailActivity.this, "Gagal memuat detail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantItem> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
