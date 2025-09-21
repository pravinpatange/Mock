package com.phonepe.demo.phonepe_mock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    private static final String FROM_EMAIL = "noreply@phonepe-mock.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLoginNotification(String toEmail, String firstName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(toEmail);
            message.setSubject("Login Notification - PhonePe Mock");

            String body = String.format(
                    "Hello %s,\n\n" +
                            "We noticed a successful login to your PhonePe Mock account at %s.\n\n" +
                            "If this wasn't you, please contact our support team immediately.\n\n" +
                            "Best regards,\n" +
                            "PhonePe Mock Team",
                    firstName,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            );

            message.setText(body);
            mailSender.send(message);
            log.info("Login notification sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send login notification to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email notification", e);
        }
    }

    public void sendWelcomeEmail(String toEmail, String firstName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(toEmail);
            message.setSubject("Welcome to PhonePe Mock!");

            String body = String.format(
                    "Hello %s,\n\n" +
                            "Welcome to PhonePe Mock! Your account has been created successfully.\n\n" +
                            "You can now:\n" +
                            "• Add bank accounts\n" +
                            "• Transfer money to other users\n" +
                            "• View transaction history\n" +
                            "• Manage your profile\n\n" +
                            "Thank you for joining PhonePe Mock!\n\n" +
                            "Best regards,\n" +
                            "PhonePe Mock Team",
                    firstName
            );

            message.setText(body);
            mailSender.send(message);
            log.info("Welcome email sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", toEmail, e);
        }
    }

    public void sendMoneyDeductedNotification(String toEmail, String firstName, BigDecimal amount,
                                              String receiverName, String transactionId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(toEmail);
            message.setSubject("Money Transfer - Amount Debited");

            String body = String.format(
                    "Hello %s,\n\n" +
                            "₹%.2f has been debited from your account.\n\n" +
                            "Transaction Details:\n" +
                            "• Amount: ₹%.2f\n" +
                            "• Sent to: %s\n" +
                            "• Transaction ID: %s\n" +
                            "• Date: %s\n\n" +
                            "Thank you for using PhonePe Mock!\n\n" +
                            "Best regards,\n" +
                            "PhonePe Mock Team",
                    firstName, amount, amount, receiverName, transactionId,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            );

            message.setText(body);
            mailSender.send(message);
            log.info("Money deducted notification sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send money deducted notification to: {}", toEmail, e);
        }
    }

    public void sendMoneyReceivedNotification(String toEmail, String firstName, BigDecimal amount,
                                              String senderName, String transactionId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(toEmail);
            message.setSubject("Money Transfer - Amount Credited");

            String body = String.format(
                    "Hello %s,\n\n" +
                            "₹%.2f has been credited to your account.\n\n" +
                            "Transaction Details:\n" +
                            "• Amount: ₹%.2f\n" +
                            "• Received from: %s\n" +
                            "• Transaction ID: %s\n" +
                            "• Date: %s\n\n" +
                            "Thank you for using PhonePe Mock!\n\n" +
                            "Best regards,\n" +
                            "PhonePe Mock Team",
                    firstName, amount, amount, senderName, transactionId,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            );

            message.setText(body);
            mailSender.send(message);
            log.info("Money received notification sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send money received notification to: {}", toEmail, e);
        }
    }
}
