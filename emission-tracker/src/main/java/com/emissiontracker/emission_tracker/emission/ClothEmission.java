package com.emissiontracker.emission_tracker.emission;

import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class ClothEmission {
    private double totalClothEmission;

    private double emissionByWoolenClothes;
    private double emissionByCottonClothes;
    private double emissionBySilkClothes;
    private double emissionByPolyesterOrNylonClothes;

    public void total(){
        totalClothEmission =emissionByWoolenClothes + emissionByCottonClothes +
                emissionBySilkClothes + emissionByPolyesterOrNylonClothes;
    }

}

