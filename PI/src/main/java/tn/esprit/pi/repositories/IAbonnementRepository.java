package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Abonnement;
@Repository
public interface IAbonnementRepository extends CrudRepository <Abonnement, Long> {
}
