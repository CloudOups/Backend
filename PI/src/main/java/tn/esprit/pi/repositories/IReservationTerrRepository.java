package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.ReservationTerrain;

public interface IReservationTerrRepository extends CrudRepository<ReservationTerrain,Integer> {
}
