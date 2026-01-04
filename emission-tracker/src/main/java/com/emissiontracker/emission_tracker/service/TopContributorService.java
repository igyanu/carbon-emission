package com.emissiontracker.emission_tracker.service;

import com.emissiontracker.emission_tracker.emission.*;
import com.emissiontracker.emission_tracker.entity.CategoryValue;
import com.emissiontracker.emission_tracker.entity.TopContributor;
import com.emissiontracker.emission_tracker.repository.TopContributorRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Component
public class TopContributorService {

    private final TopContributorRepository topContributorRepository;

    public TopContributor gettingTopContributor(
            double totalEmission,
            ClothEmission clothEmission,
            ElectricityEmission electricityEmission,
            FoodEmission foodEmission,
            TransportEmission transportEmission,
            WasteGenerationEmission wasteGenerationEmission) {

        Map<String, Double> map = new LinkedHashMap<>();

        mappingClothEmission(map, clothEmission);
        mappingFoodEmission(map, foodEmission);
        mappingElectricityEmission(map, electricityEmission);
        mappingTravelEmission(map, transportEmission);
        mappingWasteEmission(map, wasteGenerationEmission);

        map.put("Total Emission", totalEmission);

        // Get top 6 contributors (including total)
        Map<String, Double> topSix = filtering(map, 6);

        ArrayList<CategoryValue> list = new ArrayList<>();
        for (Map.Entry<String, Double> entry : topSix.entrySet()) {
            list.add(new CategoryValue(entry.getKey(), entry.getValue()));
        }

        TopContributor topContributor = new TopContributor();
        topContributor.setTopFiveContributors(list);
        topContributor.setDate(LocalDate.now());

        return topContributorRepository.save(topContributor);
    }

    public void deleteById(String id) {
        topContributorRepository.deleteById(id);
    }

    private void mappingClothEmission(Map<String, Double> map, ClothEmission clothEmission) {
        if (clothEmission.getEmissionByWoolenClothes() > 0) {
            map.put("Woolen Cloth", clothEmission.getEmissionByWoolenClothes());
        }
        if (clothEmission.getEmissionByCottonClothes() > 0) {
            map.put("Cotton Cloth", clothEmission.getEmissionByCottonClothes());
        }
        if (clothEmission.getEmissionBySilkClothes() > 0) {
            map.put("Silk Cloth", clothEmission.getEmissionBySilkClothes());
        }
        if (clothEmission.getEmissionByPolyesterOrNylonClothes() > 0) {
            map.put("Nylon/Polyester Cloth", clothEmission.getEmissionByPolyesterOrNylonClothes());
        }
    }

    private void mappingFoodEmission(Map<String, Double> map, FoodEmission foodEmission) {
        if (foodEmission.getEmissionByBeef() > 0) {
            map.put("Beef", foodEmission.getEmissionByBeef());
        }
        if (foodEmission.getEmissionByMutton() > 0) {
            map.put("Mutton/Lamb", foodEmission.getEmissionByMutton());
        }
        if (foodEmission.getEmissionByPorkOrChicken() > 0) {
            map.put("Pork/Chicken", foodEmission.getEmissionByPorkOrChicken());
        }
        if (foodEmission.getEmissionByFish() > 0) {
            map.put("Fish", foodEmission.getEmissionByFish());
        }
        if (foodEmission.getEmissionByOtherNonVegFood() > 0) {
            map.put("Other Non-Veg", foodEmission.getEmissionByOtherNonVegFood());
        }
        if (foodEmission.getEmissionByCheese() > 0) {
            map.put("Cheese", foodEmission.getEmissionByCheese());
        }
        if (foodEmission.getEmissionByRice() > 0) {
            map.put("Rice", foodEmission.getEmissionByRice());
        }
        if (foodEmission.getEmissionByOtherVegFood() > 0) {
            map.put("Other Veg Food", foodEmission.getEmissionByOtherVegFood());
        }
    }

    private void mappingElectricityEmission(Map<String, Double> map, ElectricityEmission electricityEmission) {
        if (electricityEmission.getEmissionByHeatingOrColling() > 0) {
            map.put("Heating/Cooling", electricityEmission.getEmissionByHeatingOrColling());
        }
        if (electricityEmission.getEmissionByLedLights() > 0) {
            map.put("LED Lights", electricityEmission.getEmissionByLedLights());
        }
        if (electricityEmission.getEmissionByTubeLights() > 0) {
            map.put("Tube Lights", electricityEmission.getEmissionByTubeLights());
        }
        if (electricityEmission.getEmissionByWashingMachineWithDryer() > 0) {
            map.put("Washing Machine (With Dryer)", electricityEmission.getEmissionByWashingMachineWithDryer());
        }
        if (electricityEmission.getEmissionByWashingMachineWithoutDryer() > 0) {
            map.put("Washing Machine (No Dryer)", electricityEmission.getEmissionByWashingMachineWithoutDryer());
        }
        if (electricityEmission.getEmissionByWaterHeating() > 0) {
            map.put("Water Heating", electricityEmission.getEmissionByWaterHeating());
        }
    }

    private void mappingTravelEmission(Map<String, Double> map, TransportEmission transportEmission) {
        if (transportEmission.getEmissionByPlane() > 0) {
            map.put("Plane", transportEmission.getEmissionByPlane());
        }
        if (transportEmission.getEmissionByBus() > 0) {
            map.put("Bus", transportEmission.getEmissionByBus());
        }
        if (transportEmission.getEmissionByCar() > 0) {
            map.put("Car", transportEmission.getEmissionByCar());
        }
        if (transportEmission.getEmissionByBike() > 0) {
            map.put("Bike", transportEmission.getEmissionByBike());
        }
    }

    private void mappingWasteEmission(Map<String, Double> map, WasteGenerationEmission wasteGenerationEmission) {
        if (wasteGenerationEmission.getEmissionByNonBioDegradableWaste() > 0) {
            map.put("Non-Bio Waste", wasteGenerationEmission.getEmissionByNonBioDegradableWaste());
        }
        if (wasteGenerationEmission.getEmissionByBioDegradableWaste() > 0) {
            map.put("Bio Waste", wasteGenerationEmission.getEmissionByBioDegradableWaste());
        }
    }

    private Map<String, Double> filtering(Map<String, Double> input, int n) {
        return input.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldVal, newVal) -> oldVal,
                                LinkedHashMap::new
                        )
                );
    }
}
