package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Commentaire;

public interface ICommentRepository extends JpaRepository<Commentaire, Long> {
}
