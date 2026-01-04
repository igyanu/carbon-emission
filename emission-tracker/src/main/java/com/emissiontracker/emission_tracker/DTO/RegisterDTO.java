package com.emissiontracker.emission_tracker.DTO;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterDTO {
    String username;
    String name;
    String password;
    String email;
}
