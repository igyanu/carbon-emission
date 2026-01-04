package com.emissiontracker.emission_tracker.DTO;

import lombok.Getter;

@Getter
public class WasteGenerationDTO {
    private double sharedWith;

    private double nonBioDegradable;
    private String dustbinSizeForNonBioDegradable;

    private double bioDegradable;
    private String dustbinSizeForBioDegradable;

}
