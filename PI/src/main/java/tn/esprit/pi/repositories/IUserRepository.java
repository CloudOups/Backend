package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.User;
@Repository

public interface IUserRepository extends CrudRepository<User,Long> {
}
