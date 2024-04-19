package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Publication;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface IPublicationRepository extends CrudRepository<Publication, Long> {
    List<Publication> findBlogByStatusIs(Boolean status);
}
