package tn.esprit.pi.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import tn.esprit.pi.dto.requests.AuthenticationRequest;
import tn.esprit.pi.dto.requests.RegisterRequest;
import tn.esprit.pi.dto.requests.ResetPasswordRequest;
import tn.esprit.pi.dto.responses.AuthenticationResponse;
import tn.esprit.pi.dto.responses.MessageResponse;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.services.AuthenticationService;

import javax.naming.AuthenticationException;
import java.io.IOException;


import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService ;


    @GetMapping("/test")
    public String test() {
        return "connected" ;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {
            authenticationService.register(request) ;
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage())) ;
        }
        return ResponseEntity.ok()
                .body(new MessageResponse("chec"));
    }





    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        // Supprimer la logique de gestion des tokens JWT
        return ResponseEntity.ok().body("Authentication successful");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            User user = authenticationService.login(request.getEmail(), request.getPassword());
            // Vous pouvez retourner les détails de l'utilisateur connecté ici
            return ResponseEntity.ok().body(user);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}




