package com.emissiontracker.emission_tracker.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Indexed(unique = true)
    private String email;

    @DBRef
    private ArrayList<EachFieldSummary> eachFieldSummary = new ArrayList<>();

    @DBRef
    private ArrayList<TopContributor> topContributors = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime passwordLastChanged;

}
