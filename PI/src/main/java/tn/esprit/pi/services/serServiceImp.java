package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.dto.requests.UpdateUserRequest;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;
@Service
@AllArgsConstructor
public class serServiceImp implements UserService{

    private final UserRepository userRepository ;
    private final AuthenticationService authenticationService ;


    @Override
    public User getCurrentUser(Principal connectedUser) {
        return null;
    }
}
