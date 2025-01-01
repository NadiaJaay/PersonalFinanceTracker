package com.PersonalFinance.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class JavaEmailService {

    // SMTP server configuration
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String EMAIL = "personalfinanaceteam@gmail.com";
    private static final String PASSWORD = "vxfy ifxe wzky jdqg";

    // Method to send account verification email
    public void sendVerificationEmail(String recipientEmail, String verificationLink) throws MessagingException {
        sendEmail(recipientEmail, "Verify Your Account", "Click the following link to verify your account: " + verificationLink);
    }

    // Method to send password reset email
    public void sendPasswordResetEmail(String recipientEmail, String resetLink) throws MessagingException {
        sendEmail(recipientEmail, "Reset Your Password", "Click the following link to reset your password: " + resetLink);
    }

    // Generic email sender method
    private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);

        // Create a mail session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            // Construct the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println(subject + " email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            System.out.println("Failed to send " + subject.toLowerCase() + " email to: " + recipientEmail);
            e.printStackTrace();
            throw e; // Re-throwing the exception for the calling method to handle
        }
    }
}
