package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.Terrain;

public interface ITerrainRepository extends CrudRepository<Terrain,Integer> {
}
