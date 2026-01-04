package com.emissiontracker.emission_tracker.DTO;


import com.emissiontracker.emission_tracker.emission.*;
import lombok.Data;

@Data
public class SessionEmissionData {
    private ClothEmission clothEmission = new ClothEmission();
    private ElectricityEmission electricityEmission = new ElectricityEmission();
    private FoodEmission foodEmission = new FoodEmission();
    private TransportEmission transportEmission = new TransportEmission();
    private WasteGenerationEmission wasteGenerationEmission = new WasteGenerationEmission();
}
