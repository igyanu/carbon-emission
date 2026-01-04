package com.emissiontracker.emission_tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryValue {
    private String name;     // e.g., "mutton"
    private double value;    // e.g., 32.5

}
