package com.emissiontracker.emission_tracker.DTO;

import lombok.Data;

@Data
public class OtpVerificationDTO {
    private String username;
    private int otp;
}
