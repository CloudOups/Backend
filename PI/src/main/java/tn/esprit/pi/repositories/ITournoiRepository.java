package tn.esprit.pi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Tournoi;

import java.util.List;

public interface ITournoiRepository extends JpaRepository<Tournoi,Long> {
    List<Tournoi> findByEvent(Event event);


}
