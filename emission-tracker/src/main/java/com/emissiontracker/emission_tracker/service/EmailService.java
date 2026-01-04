package com.emissiontracker.emission_tracker.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String toMail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt will expire in 5 minutes.");
        javaMailSender.send(message);
    }
}
