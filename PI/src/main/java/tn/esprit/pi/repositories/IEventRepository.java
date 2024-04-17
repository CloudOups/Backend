package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Event;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event,Long> {

    Event findByNomevent(String nom);
}
