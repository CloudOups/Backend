package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Terrain;

public interface ITerrainRepository extends JpaRepository<Terrain,Long> {
}
