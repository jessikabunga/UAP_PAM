package com.example.uap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uap.adapter.PlantAdapter;
import com.example.uap.model.PlantItem;
import com.example.uap.network.ApiClient;
import com.example.uap.network.ApiService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private List<PlantItem> plantList = new ArrayList<>();
    private MaterialButton btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlantAdapter(plantList, this);
        recyclerView.setAdapter(adapter);

        btnTambah = findViewById(R.id.btnAddList);
        btnTambah.setOnClickListener(view -> {
            startActivity(new Intent(this, AddItemActivity.class));
        });

        fetchPlantData();
    }

    private void fetchPlantData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<PlantItem>> call = apiService.getAllPlants();

        call.enqueue(new Callback<List<PlantItem>>() {
            @Override
            public void onResponse(Call<List<PlantItem>> call, Response<List<PlantItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    plantList.clear();
                    plantList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PlantItem>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
