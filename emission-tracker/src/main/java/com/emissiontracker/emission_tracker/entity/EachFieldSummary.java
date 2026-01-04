package com.emissiontracker.emission_tracker.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "summary_of_each_field")
public class EachFieldSummary {

    @Id
    private String id;

    private double totalEmission;

    private double clothEmission;
    private double foodEmission;
    private double electricityEmission;
    private double transportEmission;
    private double wasteEmission;

    private LocalDate date;
}
