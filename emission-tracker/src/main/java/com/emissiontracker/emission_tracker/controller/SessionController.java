package com.emissiontracker.emission_tracker.controller;

import com.emissiontracker.emission_tracker.DTO.*;
import com.emissiontracker.emission_tracker.service.EmissionCalculationService;
import com.emissiontracker.emission_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
@SessionAttributes("SessionData")
public class SessionController {

    private final EmissionCalculationService emissionCalculationService;
    private final UserService userService;

    @ModelAttribute("SessionData")
    public SessionEmissionData sessionData() {
        return new SessionEmissionData();
    }

    @PostMapping("/cloth")
    public ResponseEntity<String> cloth(
            @ModelAttribute("SessionData") SessionEmissionData sessionEmissionData,
            @RequestBody ClothDTO clothDTO) {

        emissionCalculationService.clothEmission(
                sessionEmissionData.getClothEmission(),
                clothDTO
        );
        return ResponseEntity.ok("Cloth emission calculated");
    }

    @PostMapping("/food")
    public ResponseEntity<String> food(
            @ModelAttribute("SessionData") SessionEmissionData sessionEmissionData,
            @RequestBody FoodDTO foodDTO) {

        emissionCalculationService.foodEmission(
                sessionEmissionData.getFoodEmission(),
                foodDTO
        );
        return ResponseEntity.ok("Food emission calculated");
    }

    @PostMapping("/electricity")
    public ResponseEntity<String> electricity(
            @ModelAttribute("SessionData") SessionEmissionData sessionEmissionData,
            @RequestBody ElectricityDTO electricityDTO) {

        emissionCalculationService.electricityEmission(
                sessionEmissionData.getElectricityEmission(),
                electricityDTO
        );
        return ResponseEntity.ok("Electricity emission calculated");
    }

    @PostMapping("/transportation")
    public ResponseEntity<String> transport(
            @ModelAttribute("SessionData") SessionEmissionData sessionEmissionData,
            @RequestBody TransportationDTO transportationDTO) {

        emissionCalculationService.transportEmission(
                sessionEmissionData.getTransportEmission(),
                transportationDTO
        );
        return ResponseEntity.ok("Transport emission calculated");
    }

    @PostMapping("/waste")
    public ResponseEntity<String> waste(
            @ModelAttribute("SessionData") SessionEmissionData sessionEmissionData,
            @RequestBody WasteGenerationDTO wasteGenerationDTO) {

        emissionCalculationService.emissionByWaste(
                sessionEmissionData.getWasteGenerationEmission(),
                wasteGenerationDTO
        );
        return ResponseEntity.ok("Waste emission calculated");
    }

    @GetMapping("/summary")
    public ResponseEntity<String> summary(
            @ModelAttribute("SessionData") SessionEmissionData session,
            Authentication authentication) {

        String username = authentication.getName();

        userService.createSummary(
                username,
                session.getClothEmission(),
                session.getElectricityEmission(),
                session.getFoodEmission(),
                session.getTransportEmission(),
                session.getWasteGenerationEmission()
        );

        return ResponseEntity.ok("Summary created successfully");
    }
}
