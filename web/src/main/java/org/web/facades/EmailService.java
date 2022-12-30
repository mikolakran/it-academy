package org.web.facades;

public interface EmailService {
    void sendEmail(String toAddress, String formAddress, String subject, String msgBody);
}
