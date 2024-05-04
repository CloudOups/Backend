package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Tournoi;

public interface ITournoiRepository extends JpaRepository<Tournoi,Long> {
}
