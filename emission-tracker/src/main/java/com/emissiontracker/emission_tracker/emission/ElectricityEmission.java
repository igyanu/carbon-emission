package com.emissiontracker.emission_tracker.emission;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ElectricityEmission {
    private double totalElectricEmission;

    private double emissionByHeatingOrColling;
    private double emissionByLedLights;
    private double emissionByTubeLights;

    private double emissionByWashingMachineWithDryer;
    private double emissionByWashingMachineWithoutDryer;

    private double emissionByWaterHeating;

    public void total(double sharedWith){
        totalElectricEmission = emissionByWashingMachineWithoutDryer + emissionByWashingMachineWithDryer +
                emissionByWaterHeating;
        if (sharedWith>1){
            emissionByHeatingOrColling /= sharedWith;
            emissionByLedLights /= sharedWith;

            emissionByTubeLights /= sharedWith;
        }
        double common = emissionByHeatingOrColling + emissionByLedLights + emissionByTubeLights;
        totalElectricEmission += common;

    }
}
