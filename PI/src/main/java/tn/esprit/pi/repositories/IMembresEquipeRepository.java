package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.MembresEquipe;

@Repository
public interface IMembresEquipeRepository extends CrudRepository<MembresEquipe, Equipe> {

   // void deleteByEquipeId(Long equipeID);
}
