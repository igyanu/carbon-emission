package com.emissiontracker.emission_tracker.DTO;

import lombok.Getter;

@Getter
public class FoodDTO {
    // non-veg
    private double beef;
    private double mutton;
    private double porkOrChicken;
    private double fish;
    private double egg;
    private double otherNonVegFood;

    // veg
    private double cheese;
    private double rice;
    private double otherVegFood;
}
