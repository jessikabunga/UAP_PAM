package com.example.uap;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uap.model.PlantItem;
import com.example.uap.network.ApiClient;
import com.example.uap.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    private EditText etPlantName, etPrice, etDescription;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etPlantName = findViewById(R.id.etPlantName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> addPlantToApi());
    }

    private void addPlantToApi() {
        String name = etPlantName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        PlantItem plantItem = new PlantItem();
        plantItem.setPlantName(name);
        plantItem.setPrice(price);
        plantItem.setDescription(description);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PlantItem> call = apiService.addPlant(plantItem);

        call.enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(Call<PlantItem> call, Response<PlantItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish(); // kembali ke halaman sebelumnya
                } else {
                    Toast.makeText(AddItemActivity.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantItem> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
