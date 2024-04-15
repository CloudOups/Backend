package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.TypeTerrain;

import java.time.LocalDateTime;

public interface IReservationTerrRepository extends CrudRepository<ReservationTerrain,Integer> {
   // boolean existsByDateDebutBetweenAndAndDateFin(LocalDateTime dateDebut, LocalDateTime dateFin);
    boolean existsByDateFinBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
    boolean existsByTerrainTypeTerrainAndDateFinBetween(TypeTerrain typeTerrain, LocalDateTime dateDebut, LocalDateTime dateFin);
}

