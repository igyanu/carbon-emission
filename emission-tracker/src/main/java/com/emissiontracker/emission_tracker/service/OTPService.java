package com.emissiontracker.emission_tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final EmailService emailService;
    private final StringRedisTemplate redisTemplate;

    public void sendOTP(String username, String emailId) {
        String otp = otpGenerator();
        redisTemplate.opsForValue().set(username, otp, 5, TimeUnit.MINUTES);
        emailService.sendEmail(emailId, otp);
    }
    public void verifyOTP(String username, int userProvidedOtp) {
        String otp = String.valueOf(userProvidedOtp);
        String saved= redisTemplate.opsForValue().get(username);
         if(saved!=null && saved.equals(otp) ) return;
        throw new ResponseStatusException(HttpStatus.FORBIDDEN ,"otp not verified");
    }
    private String otpGenerator() {
        int otp = (int) ( Math.random() * 900000) + 100000;
        if(otp<=100000 || otp>=999999) return otpGenerator();
        return String.valueOf(otp);
    }
}
