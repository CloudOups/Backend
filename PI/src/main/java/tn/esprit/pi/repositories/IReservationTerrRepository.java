package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;

import java.util.List;

@Repository

public interface IReservationTerrRepository extends CrudRepository<ReservationTerrain,Long> {
    List<ReservationTerrain> findReserByEtatReser(boolean etatReser);

}
