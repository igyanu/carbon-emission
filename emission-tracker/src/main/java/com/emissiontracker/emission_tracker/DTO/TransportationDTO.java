package com.emissiontracker.emission_tracker.DTO;

import lombok.Getter;

@Getter
public class TransportationDTO {

    private double travelDistanceByPlane;
    private double travelDistanceByBus;
    private double travelDistanceByCar;
    private double travelDistanceByBike;

}
