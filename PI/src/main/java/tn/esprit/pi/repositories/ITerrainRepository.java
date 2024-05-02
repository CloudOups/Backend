package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeTerrain;

import java.util.Collection;
import java.util.List;

@Repository

public interface ITerrainRepository extends JpaRepository<Terrain,Long> {
    List<Terrain> findByStatusTerrain(StatusTerrain statusTerrain);
    List<Terrain> findByTypeTerrain(TypeTerrain typeTerrain);

    Terrain findByNomTerrain(String nom);

}
