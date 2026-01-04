package com.emissiontracker.emission_tracker.service;

import com.emissiontracker.emission_tracker.DTO.*;
import com.emissiontracker.emission_tracker.constant.Constants;
import com.emissiontracker.emission_tracker.emission.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Getter
public class EmissionCalculationService {

    public void clothEmission( ClothEmission clothEmission, ClothDTO clothDTO){
        clothEmission.setEmissionByWoolenClothes(clothDTO.getWoolen()*Constants.ClothesEmission.WOOL_CO2);
        clothEmission.setEmissionByCottonClothes(clothDTO.getCotton()*Constants.ClothesEmission.COTTON_CO2);
        clothEmission.setEmissionBySilkClothes(clothDTO.getSilk()*Constants.ClothesEmission.SILK_CO2);
        clothEmission.setEmissionByPolyesterOrNylonClothes(clothDTO.getPolysterOrNylon()*Constants.ClothesEmission.POLYESTER_OR_NYLON_CO2);

        clothEmission.total();
    }

    public void foodEmission(FoodEmission foodEmission, FoodDTO foodDTO){
        foodEmission.setEmissionByBeef( foodDTO.getBeef()*Constants.FoodEmission.BEEF_CO2 );
        foodEmission.setEmissionByMutton( foodDTO.getMutton() * Constants.FoodEmission.MUTTON_CO2 );
        foodEmission.setEmissionByPorkOrChicken( foodDTO.getPorkOrChicken() * Constants.FoodEmission.PORK_OR_CHICKEN_CO2 );
        foodEmission.setEmissionByFish( foodDTO.getFish() * Constants.FoodEmission.FISH_CO2 );
        foodEmission.setEmissionByOtherNonVegFood( foodDTO.getOtherNonVegFood() * Constants.FoodEmission.OTHER_NONVEG_FOOD_CO2 );

        foodEmission.setEmissionByCheese( foodDTO.getCheese() * Constants.FoodEmission.CHEESE_CO2 );
        foodEmission.setEmissionByRice( foodDTO.getRice() * Constants.FoodEmission.RICE_CO2 );
        foodEmission.setEmissionByOtherVegFood( foodDTO.getOtherVegFood() * Constants.FoodEmission.OTHER_VEG_FOOD_CO2 );
        foodEmission.total();
    }

    public void electricityEmission(ElectricityEmission electricityEmission, ElectricityDTO electricityDTO){
        electricityEmission.setEmissionByHeatingOrColling(
                electricityDTO.getRoomHeatingOrCollingTime() * Constants.HouseholdEmission.HEATING_OR_COOLING_CO2 * 30
        );
        electricityEmission.setEmissionByLedLights(
                electricityDTO.getLedLights() * Constants.HouseholdEmission.LED_LIGHTS_CO2 * 30
        );
        electricityEmission.setEmissionByTubeLights(
                electricityDTO.getTubeLights() * Constants.HouseholdEmission.TUBE_LIGHTS_CO2 * 30
        );
        electricityEmission.setEmissionByWashingMachineWithDryer(
                electricityDTO.getWashingMachineWithDryerLoadCount() * Constants.HouseholdEmission.WASHING_WITH_DRYER_CO2 * 4
        );
        electricityEmission.setEmissionByWashingMachineWithoutDryer(
                electricityDTO.getWashingMachineWithoutDryerLoadCount() * Constants.HouseholdEmission.WASHING_WITHOUT_DRYER_CO2 * 4
        );
        electricityEmission.setEmissionByWaterHeating(
                electricityDTO.getWaterHeatingTime() * Constants.HouseholdEmission.WATER_HEATING_CO2 * 30
        );
        electricityEmission.total(electricityDTO.getSharedWith());

    }

    public void transportEmission(TransportEmission transportEmission, TransportationDTO transportationDTO){
        transportEmission.setEmissionByPlane(
                transportationDTO.getTravelDistanceByPlane() * Constants.TransportationEmission.PLANE_CO2
        );
        transportEmission.setEmissionByBus(
                transportationDTO.getTravelDistanceByBus() * Constants.TransportationEmission.BUS_CO2
        );
        transportEmission.setEmissionByCar(
                transportationDTO.getTravelDistanceByCar() * Constants.TransportationEmission.CAR_CO2
        );
        transportEmission.setEmissionByBike(
                transportationDTO.getTravelDistanceByBike() * Constants.TransportationEmission.BIKE_CO2
        );
        transportEmission.total();
    }

    public void emissionByWaste(WasteGenerationEmission wasteGenerationEmission ,WasteGenerationDTO  wasteGenerationDTO ){
        String nonBioSize = wasteGenerationDTO.getDustbinSizeForNonBioDegradable();
        float nonBioConstant=0;
        if ( !nonBioSize.isBlank() && wasteGenerationDTO.getNonBioDegradable()>0 ){
            if (nonBioSize.toLowerCase().equals("small")) nonBioConstant = Constants.WasteGenerationEmission.NON_BIO_SMALL_CO2;
            else if (nonBioSize.toLowerCase().equals("medium")) nonBioConstant = Constants.WasteGenerationEmission.NON_BIO_MEDIUM_CO2;
            else if (nonBioSize.toLowerCase().equals("large")) nonBioConstant = Constants.WasteGenerationEmission.NON_BIO_LARGE_CO2;
        }

        String bioSize = wasteGenerationDTO.getDustbinSizeForBioDegradable();
        float bioConstant = 0 ;
        if ( !bioSize.isBlank() && wasteGenerationDTO.getBioDegradable()>0) {
            if (bioSize.toLowerCase().equals("small")) bioConstant = Constants.WasteGenerationEmission.BIO_SMALL_CO2;
            else if (bioSize.toLowerCase().equals("medium"))
                bioConstant = Constants.WasteGenerationEmission.BIO_MEDIUM_CO2;
            else if (bioSize.toLowerCase().equals("large")) bioConstant = Constants.WasteGenerationEmission.BIO_LARGE_CO2;
        }

        wasteGenerationEmission.setEmissionByNonBioDegradableWaste(
                wasteGenerationDTO.getNonBioDegradable() * nonBioConstant * 4
        );
        wasteGenerationEmission.setEmissionByBioDegradableWaste(
                wasteGenerationDTO.getBioDegradable() * bioConstant * 4
        );
        wasteGenerationEmission.total(wasteGenerationDTO.getSharedWith());
    }


}
