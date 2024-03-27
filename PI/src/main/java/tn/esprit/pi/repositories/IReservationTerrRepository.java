package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.ReservationTerrain;
@Repository

public interface IReservationTerrRepository extends CrudRepository<ReservationTerrain,Long> {
}
