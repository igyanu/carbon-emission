package com.emissiontracker.emission_tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "top_contributor")
public class TopContributor {
    @Id
    private String id;

    private ArrayList<CategoryValue> topFiveContributors;
    private LocalDate date;
}
