package com.markuvweb.portfolio.controller;

import com.markuvweb.portfolio.model.dto.EmailDataDTO;
import com.markuvweb.portfolio.service.EmailService;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8080","https://markuvtest.website","https://portfolio-zezula.website"}, allowCredentials = "true")
public class EmailController {

    @Autowired
    private EmailService emailService;

    private final String pathToCV;
    public EmailController(@Value("${pathToCV}") String pathToCV) {
        this.pathToCV = pathToCV;
    }
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDataDTO emailDataDTO){
        String text =   "Email od: "+ emailDataDTO.getEmail() + "\n" +
                        "Jmeno: " + emailDataDTO.getName() + "\n\n" +
                        emailDataDTO.getText();
        emailService.sendEmail("ma43k.zez@gmail.com", emailDataDTO.getSubject(), text);
        return ResponseEntity.ok("E-mail úspěšně odeslán");
    }
    @PostMapping("/cv")
    public ResponseEntity<String> sendCV(@RequestParam String emailName) throws Exception {
        emailService.sendCV(emailName, "CV","",pathToCV,"Marek Zezula CV.pdf" );
        System.out.println(pathToCV);
        return ResponseEntity.ok("CV úspěšně odesláno");
    }
}
