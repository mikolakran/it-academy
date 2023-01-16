package com.web.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toAddress, String formAddress, String subject, String msgBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(formAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(msgBody);
        javaMailSender.send(message);
    }
}
