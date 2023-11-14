package com.markuvweb.portfolio.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    public void sendCV(String to, String subject, String text, String pathToCV, String CVName) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        byte[] CVByteData = loadAttachmentAsBytes(pathToCV);

        helper.addAttachment(CVName, new ByteArrayResource(CVByteData, "application/pdf"));
        javaMailSender.send(message);
    }
    private byte[] loadAttachmentAsBytes(String pathToAttachment) throws IOException {
        File attachmentFile = new File(pathToAttachment);
        return Files.readAllBytes(attachmentFile.toPath());
    }

}

