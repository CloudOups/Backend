package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.User;

import java.util.Optional;

@Repository

public interface IEquipeRepository extends CrudRepository <Equipe,Long>{
    Equipe findByNomEquipe(String nomEquipe);
}
