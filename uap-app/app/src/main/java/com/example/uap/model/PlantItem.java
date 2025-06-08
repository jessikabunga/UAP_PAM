package com.example.uap.model;

import com.google.gson.annotations.SerializedName;

public class PlantItem {
    @SerializedName("id")
    private int id;

    @SerializedName("plant_name")
    private String plantName;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private String price;

    // Getter dan Setter

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
