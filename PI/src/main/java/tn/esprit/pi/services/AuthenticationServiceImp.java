package tn.esprit.pi.services;

import com.google.zxing.NotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tn.esprit.pi.dto.requests.AuthenticationRequest;
import tn.esprit.pi.dto.requests.RegisterRequest;
import tn.esprit.pi.dto.responses.AuthenticationResponse;
import tn.esprit.pi.entities.ResetPasswordToken;
import tn.esprit.pi.entities.Role;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.entities.VerifyAccountToken;
import tn.esprit.pi.repositories.ResetPasswordTokenRepository;
import tn.esprit.pi.repositories.UserRepository;
import tn.esprit.pi.repositories.VerifyAccountTokenRepository;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository ;




    @Value("${BASE_API_URL}")
    private String baseApiUrl ;


    public void register(RegisterRequest request) throws MessagingException {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password((request.getPassword()))
                .role(Role.Etudiant)
                .registrationDate(new Date())
                .enabled(true)
                .build() ;

        userRepository.save(user) ;
    }









    public User login(String email, String password) throws AuthenticationException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Email not found"));

        if (!user.isEnabled()) {
            throw new AuthenticationException("User account is disabled");
        }

        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid password");
        }

        return user;
    }
}











