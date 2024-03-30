package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Publication;

public interface IPublicationRepository extends JpaRepository<Publication, Long> {
}
