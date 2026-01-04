package com.emissiontracker.emission_tracker.service;

import com.emissiontracker.emission_tracker.DTO.RegisterDTO;
import com.emissiontracker.emission_tracker.DTO.LoginDTO;
import com.emissiontracker.emission_tracker.emission.*;
import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import com.emissiontracker.emission_tracker.entity.TopContributor;
import com.emissiontracker.emission_tracker.entity.User;
import com.emissiontracker.emission_tracker.repository.EachFieldSummaryRepository;
import com.emissiontracker.emission_tracker.repository.TopContributorRepository;
import com.emissiontracker.emission_tracker.repository.UserRepository;
import com.emissiontracker.emission_tracker.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EachFieldSummaryRepository eachFieldSummaryRepository;
    private final TopContributorRepository topContributorRepository;
    private final TopContributorService topContributorService;

    public void createUser(RegisterDTO registerDTO) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .username(registerDTO.getUsername())
                .name(registerDTO.getName())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .createdAt(now)
                .passwordLastChanged(now)
                .build();

        userRepository.save(user);
    }

    public String login(LoginDTO loginDTO) {
        User user = getUserByUsingUsernameOrEmail(loginDTO.getUsernameOrEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    public User getUserByUsingUsernameOrEmail(String usernameOrEmail) {
        if (userRepository.existsByUsername(usernameOrEmail)) {
            return userRepository.findByUsername(usernameOrEmail);
        } else if (userRepository.existsByEmail(usernameOrEmail)) {
            return userRepository.findByEmail(usernameOrEmail);
        }
        return null;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void changeUsername(String currentUsername, String newUsername) {
        // Check if new username already exists
        if (userRepository.existsByUsername(newUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Username already taken");
        }

        User user = getUserByUsername(currentUsername);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = getUserByUsername(username);

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Old password is incorrect");
        }

        // Update password and timestamp
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordLastChanged(LocalDateTime.now());
        userRepository.save(user);
    }

    public void createSummary(
            String username,
            ClothEmission clothEmission,
            ElectricityEmission electricityEmission,
            FoodEmission foodEmission,
            TransportEmission transportEmission,
            WasteGenerationEmission wasteGenerationEmission) {

        // Create and save EachFieldSummary
        EachFieldSummary eachFieldSummary = EachFieldSummary.builder()
                .clothEmission(clothEmission.getTotalClothEmission())
                .electricityEmission(electricityEmission.getTotalElectricEmission())
                .foodEmission(foodEmission.getTotalEmissionByFood())
                .transportEmission(transportEmission.getTotalTransportEmission())
                .wasteEmission(wasteGenerationEmission.getTotalEmissionByWaste())
                .totalEmission(
                        clothEmission.getTotalClothEmission() +
                                electricityEmission.getTotalElectricEmission() +
                                foodEmission.getTotalEmissionByFood() +
                                transportEmission.getTotalTransportEmission() +
                                wasteGenerationEmission.getTotalEmissionByWaste()
                )
                .date(java.time.LocalDate.now())
                .build();

        eachFieldSummaryRepository.save(eachFieldSummary);

        // Create and save TopContributor
        TopContributor topContributor = topContributorService.gettingTopContributor(
                eachFieldSummary.getTotalEmission(),
                clothEmission,
                electricityEmission,
                foodEmission,
                transportEmission,
                wasteGenerationEmission
        );

        // Add references to user
        User user = userRepository.findByUsername(username);
        if (user.getEachFieldSummary() == null) {
            user.setEachFieldSummary(new java.util.ArrayList<>());
        }
        if (user.getTopContributors() == null) {
            user.setTopContributors(new java.util.ArrayList<>());
        }

        user.getEachFieldSummary().add(eachFieldSummary);
        user.getTopContributors().add(topContributor);
        userRepository.save(user);
    }
}
