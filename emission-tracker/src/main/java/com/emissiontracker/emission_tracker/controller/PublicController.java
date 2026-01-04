package com.emissiontracker.emission_tracker.controller;

import com.emissiontracker.emission_tracker.DTO.RegisterDTO;
import com.emissiontracker.emission_tracker.DTO.LoginDTO;
import com.emissiontracker.emission_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity healthCheck() {
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.createUser(registerDTO);
        }
        catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LoginDTO loginDTO) throws Exception {
        String token = userService.login(loginDTO);
        return new ResponseEntity(token, HttpStatus.OK);
    }


}
