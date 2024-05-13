package tn.esprit.pi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.EmailRequest;
import tn.esprit.pi.entities.EmailRequest2;
import tn.esprit.pi.services.EmailServices;
import tn.esprit.pi.services.IQrEmailService;

@RestController
@CrossOrigin(origins = "*")

public class EmailController {
    private EmailServices emailService;
    private IQrEmailService iQrEmailService;
    // Constructor injection
    public EmailController(EmailServices emailService,IQrEmailService iQrEmailService) {
        this.emailService = emailService;
        this.iQrEmailService = iQrEmailService;

    }

    //api to send email
    //api to send email
    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)

    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        System.out.println(request);
        boolean result = this.emailService.sendEmail(request.getSubject(), request.getMessage(),request.getTo());
        if (result) {
            return ResponseEntity.ok("Email is sent successfully...");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( "Email not sent");
        }
    }



//api to send email
//api to send email
@RequestMapping(value = "/sendemailQr", method = RequestMethod.POST)

public ResponseEntity<?> sendEmailQr(@RequestBody EmailRequest2 request2) {
    System.out.println(request2);
    boolean result = this.iQrEmailService.sendEmailWithQRCode(request2.getSubject(), request2.getMessage(),request2.getTo(),request2.getQrImg());
    if (result) {
        return ResponseEntity.ok("Email is sent successfully...");
    } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( "Email not sent");
    }
}
}