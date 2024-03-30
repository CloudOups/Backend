package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByPublication(Publication publication);
}
