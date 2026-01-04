package com.emissiontracker.emission_tracker.emission;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class FoodEmission {
    private double totalEmissionByFood;

    // non-veg
    private double totalEmissionByNonVegFood;

    private double emissionByBeef;
    private double emissionByMutton;
    private double emissionByPorkOrChicken;
    private double emissionByFish;
    private double emissionByEgg;
    private double emissionByOtherNonVegFood;

    // veg
    private double totalEmissionByVegFood;
    
    private double emissionByCheese;
    private double emissionByRice;
    private double emissionByOtherVegFood;

    public void total(){
        totalEmissionByVegFood = emissionByCheese + emissionByRice + emissionByOtherVegFood;
        totalEmissionByNonVegFood = emissionByBeef + emissionByMutton + emissionByPorkOrChicken +
                emissionByFish + emissionByEgg + emissionByOtherNonVegFood;
        totalEmissionByFood = totalEmissionByVegFood + totalEmissionByNonVegFood;
    }
}
