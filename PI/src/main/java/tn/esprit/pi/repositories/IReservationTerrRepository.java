package tn.esprit.pi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeReservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface IReservationTerrRepository extends JpaRepository<ReservationTerrain,Long> {
    List<ReservationTerrain> findReserByEtatReser(boolean etatReser);
    List<ReservationTerrain> findByTypeRes(TypeReservation typeRes);
    List<ReservationTerrain> findByTerrain(Terrain terrain);

    List<ReservationTerrain> findByUser_UserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM ReservationTerrain r WHERE r.terrain.numTerrain = :numTerrain AND r.etatReser = true")
    Boolean existsActiveReservationsForTerrain(Long numTerrain);

    @Query("SELECT r FROM ReservationTerrain r WHERE r.dateFin < CURRENT_TIME ")
    List<ReservationTerrain> findByExpired();
}
