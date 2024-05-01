package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.StatusTerrain;
=======
>>>>>>> parent of e92394e (Merge branch 'Rania')
import tn.esprit.pi.entities.Terrain;

<<<<<<< HEAD
import java.util.Collection;
import java.util.List;

@Repository

public interface ITerrainRepository extends CrudRepository<Terrain,Long> {
    List<Terrain> findByStatusTerrain(StatusTerrain statusTerrain);
    List<Terrain> findByTypeTerrain(TypeTerrain typeTerrain);

    Terrain findByNomTerrain(String nom);

=======
public interface ITerrainRepository extends CrudRepository<Terrain,Integer> {
>>>>>>> parent of e92394e (Merge branch 'Rania')
}
