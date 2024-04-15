package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeTerrain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ITerrainRepository extends JpaRepository<Terrain,Long> {
   List<Terrain> findTerrainByTypeTerrain(TypeTerrain typeTerrain);

 //  List<Terrain> findDistinctByTypeTerrainAndReservations_DateDebutLessThanEqualAndReservations_DateFinGreaterThanEqual(TypeTerrain typeTerrain, LocalDate dateDebut, LocalDate dateFin);

   @Query("SELECT DISTINCT t FROM Terrain t JOIN t.reservations r WHERE t.typeTerrain = :typeTerrain AND r.dateDebut <= :dateFin AND r.dateFin >= :dateDebut")
   List<Terrain> findTerrainsDisponiblesByTypeAndDate(
           @Param("typeTerrain") TypeTerrain typeTerrain,
           @Param("dateDebut") LocalDateTime dateDebut,
           @Param("dateFin") LocalDateTime dateFin
   );
}
