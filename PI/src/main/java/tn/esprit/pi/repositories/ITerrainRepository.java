package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;

import java.util.Collection;
import java.util.List;

@Repository

public interface ITerrainRepository extends CrudRepository<Terrain,Long> {
    List<Terrain> findByStatusTerrain(StatusTerrain statusTerrain);

}
