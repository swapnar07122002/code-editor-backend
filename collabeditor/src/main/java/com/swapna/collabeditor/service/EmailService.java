package com.swapna.collabeditor.service;

public interface EmailService {
    void sendEmail(String to, String subject, String htmlContent);
}
