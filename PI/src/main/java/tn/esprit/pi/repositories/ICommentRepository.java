package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface ICommentRepository extends CrudRepository<Commentaire, Long> {
    List<Commentaire> findByPublication(Publication publication);
}
