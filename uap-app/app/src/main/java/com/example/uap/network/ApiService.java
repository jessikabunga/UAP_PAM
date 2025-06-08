package com.example.uap.network;

import com.example.uap.model.PlantItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("plant/all")
    Call<List<PlantItem>> getAllPlants();

    @GET("plant/{name}")
    Call<PlantItem> getPlantByName(@Path("name") String name);

    @POST("plant/new")
    Call<PlantItem> addPlant(@Body PlantItem plantItem);

    @PUT("plant/{name}")
    Call<PlantItem> updatePlant(@Path("name") String name, @Body PlantItem plantItem);

    @DELETE("plant/{name}")
    Call<Void> deletePlant(@Path("name") String name);
}

