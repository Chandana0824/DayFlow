package com.dayflow.hrms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled}")
    private boolean mailEnabled;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        String verifyLink = frontendBaseUrl + "/verify-email?token=" + token;

        if (!mailEnabled) {
            // Hackathon-friendly fallback: print the link instead of requiring real SMTP creds.
            log.info("=== EMAIL VERIFICATION (mail disabled, dev mode) ===");
            log.info("To: {}", to);
            log.info("Verify link: {}", verifyLink);
            log.info("======================================================");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Verify your Dayflow account");
        message.setText("Welcome to Dayflow!\n\nPlease verify your email by clicking the link below:\n"
                + verifyLink + "\n\nThis link expires in 24 hours.");
        mailSender.send(message);
    }
}
