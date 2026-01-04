package com.emissiontracker.emission_tracker.controller;

import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import com.emissiontracker.emission_tracker.entity.TopContributor;
import com.emissiontracker.emission_tracker.entity.User;
import com.emissiontracker.emission_tracker.service.EachFieldSummaryService;
import com.emissiontracker.emission_tracker.service.TopContributorService;
import com.emissiontracker.emission_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EachFieldSummaryService eachFieldSummaryService;
    private final TopContributorService topContributorService;

    /**
     * Get current user's account details
     */
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getUserDetails(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Map<String, Object> details = new HashMap<>();
        details.put("username", user.getUsername());
        details.put("email", user.getEmail());
        details.put("name", user.getName());
        details.put("createdAt", user.getCreatedAt());
        details.put("passwordLastChanged", user.getPasswordLastChanged());

        return ResponseEntity.ok(details);
    }

    /**
     * Get all emission summaries for current user (ordered by date - newest first)
     */
    @GetMapping("/summaries")
    public ResponseEntity<List<Map<String, Object>>> getUserSummaries(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<EachFieldSummary> summaries = user.getEachFieldSummary();
        if (summaries == null || summaries.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Sort by date (newest first)
        List<Map<String, Object>> result = summaries.stream()
                .sorted(Comparator.comparing(EachFieldSummary::getDate).reversed())
                .map(summary -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", summary.getId());
                    map.put("date", summary.getDate());
                    map.put("totalEmission", summary.getTotalEmission());
                    map.put("clothEmission", summary.getClothEmission());
                    map.put("foodEmission", summary.getFoodEmission());
                    map.put("electricityEmission", summary.getElectricityEmission());
                    map.put("transportEmission", summary.getTransportEmission());
                    map.put("wasteEmission", summary.getWasteEmission());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**
     * Delete a specific emission summary by ID
     */
    @DeleteMapping("/summaries/{summaryId}")
    public ResponseEntity<Map<String, String>> deleteSummary(
            @PathVariable String summaryId,
            Authentication authentication) {

        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Remove from user's list
        boolean removed = user.getEachFieldSummary().removeIf(
                summary -> summary.getId().equals(summaryId)
        );

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Summary not found or does not belong to user");
        }

        // Save user with updated list
        userService.saveUser(user);

        // Delete the actual summary document
        try {
            eachFieldSummaryService.deleteSummaryById(new ObjectId(summaryId));
        } catch (Exception e) {
            // Summary might already be deleted, which is okay
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Summary deleted successfully");
        response.put("deletedId", summaryId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get all top contributors for current user (ordered by date - newest first)
     */
    @GetMapping("/top-contributors")
    public ResponseEntity<List<Map<String, Object>>> getUserTopContributors(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<TopContributor> contributors = user.getTopContributors();
        if (contributors == null || contributors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Sort by date (newest first)
        List<Map<String, Object>> result = contributors.stream()
                .sorted(Comparator.comparing(TopContributor::getDate).reversed())
                .map(contributor -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", contributor.getId());
                    map.put("date", contributor.getDate());
                    map.put("topContributors", contributor.getTopFiveContributors());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**
     * Delete a specific top contributor by ID
     */
    @DeleteMapping("/top-contributors/{contributorId}")
    public ResponseEntity<Map<String, String>> deleteTopContributor(
            @PathVariable String contributorId,
            Authentication authentication) {

        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Remove from user's list
        boolean removed = user.getTopContributors().removeIf(
                contributor -> contributor.getId().equals(contributorId)
        );

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Top contributor not found or does not belong to user");
        }

        // Save user with updated list
        userService.saveUser(user);

        // Delete the actual document
        try {
            topContributorService.deleteById(contributorId);
        } catch (Exception e) {
            // Document might already be deleted
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Top contributor deleted successfully");
        response.put("deletedId", contributorId);

        return ResponseEntity.ok(response);
    }

    /**
     * Change username
     */
    @PutMapping("/change-username")
    public ResponseEntity<Map<String, String>> changeUsername(
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        String currentUsername = authentication.getName();
        String newUsername = request.get("newUsername");

        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New username is required");
        }

        userService.changeUsername(currentUsername, newUsername.trim());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Username changed successfully");
        response.put("newUsername", newUsername.trim());

        return ResponseEntity.ok(response);
    }

    /**
     * Change password
     */
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        String username = authentication.getName();
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        if (oldPassword == null || newPassword == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both old and new passwords are required");
        }

        userService.changePassword(username, oldPassword, newPassword);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password changed successfully");

        return ResponseEntity.ok(response);
    }
}
