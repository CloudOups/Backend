package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Equipe;
@Repository

public interface IEquipeRepository extends CrudRepository <Equipe,Long>{
}
