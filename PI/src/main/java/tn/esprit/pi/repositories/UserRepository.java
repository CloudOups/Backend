package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Role;
import tn.esprit.pi.entities.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username) ;
    Optional<User> findByEmailAndRole(String email, String role);
}