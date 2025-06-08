package com.example.uap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateActivity extends AppCompatActivity {

    private EditText etName, etPrice, etDescription;
    private Button btnUpdate;
    private String originalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = findViewById(R.id.etPlantName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnUpdate = findViewById(R.id.btnAdd); // ID-nya masih btnAdd, tapi dipakai untuk update

        btnUpdate.setText("Update");

        originalName = getIntent().getStringExtra("plant_name");

        if (originalName != null) {
            loadPlantData(originalName);
        } else {
            Toast.makeText(this, "Tanaman tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnUpdate.setOnClickListener(v -> updatePlantData());
    }

    private void loadPlantData(String name) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getPlantByName(name).enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(Call<PlantItem> call, Response<PlantItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlantItem plant = response.body();
                    etName.setText(plant.getPlantName());
                    etPrice.setText(plant.getPrice());
                    etDescription.setText(plant.getDescription());
                }
            }

            @Override
            public void onFailure(Call<PlantItem> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePlantData() {
        String newName = etName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (newName.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        PlantItem updatedPlant = new PlantItem();
        updatedPlant.setPlantName(newName);
        updatedPlant.setPrice(price);
        updatedPlant.setDescription(description);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.updatePlant(originalName, updatedPlant).enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(Call<PlantItem> call, Response<PlantItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Berhasil diupdate", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantItem> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
