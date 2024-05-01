package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.Tournoi;

public interface ITournoiRepository extends CrudRepository<Tournoi,Long> {
}
