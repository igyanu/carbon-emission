package com.emissiontracker.emission_tracker.emission;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class WasteGenerationEmission {
    private double totalEmissionByWaste;

    private double emissionByNonBioDegradableWaste;
    private double emissionByBioDegradableWaste;

    public void total(double sharedWith){
        if(sharedWith>1){
            emissionByNonBioDegradableWaste /= sharedWith;
            emissionByBioDegradableWaste /= sharedWith;
        }
        totalEmissionByWaste = emissionByBioDegradableWaste + emissionByNonBioDegradableWaste;
    }
}
