package tn.esprit.pi.services;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import tn.esprit.pi.dto.requests.AuthenticationRequest;
import tn.esprit.pi.dto.requests.RegisterRequest;
import tn.esprit.pi.dto.responses.AuthenticationResponse;
import tn.esprit.pi.entities.User;

import javax.naming.AuthenticationException;
import java.io.IOException;

public interface AuthenticationService {
    public void register(RegisterRequest request) throws MessagingException;
    public User login(String email, String password) throws AuthenticationException ;





}
