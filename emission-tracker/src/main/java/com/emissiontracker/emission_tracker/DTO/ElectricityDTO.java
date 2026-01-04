package com.emissiontracker.emission_tracker.DTO;

import lombok.Getter;

@Getter
public class ElectricityDTO {
    private double sharedWith;

    private double roomHeatingOrCollingTime;
    private double ledLights;
    private double tubeLights;

    private double washingMachineWithDryerLoadCount;
    private double washingMachineWithoutDryerLoadCount;

    private double waterHeatingTime;

}
