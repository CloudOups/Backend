package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Terrain;
@Repository

public interface ITerrainRepository extends CrudRepository<Terrain,Long> {
}
