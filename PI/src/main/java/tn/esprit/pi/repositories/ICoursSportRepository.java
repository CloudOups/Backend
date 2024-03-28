package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.CoursSport;

public interface ICoursSportRepository extends CrudRepository <CoursSport, Long> {
}
