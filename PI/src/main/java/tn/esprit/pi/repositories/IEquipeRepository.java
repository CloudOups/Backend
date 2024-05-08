package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.User;

import java.util.List;
import java.util.Optional;

@Repository

public interface IEquipeRepository extends JpaRepository<Equipe,Long> {
    Equipe findByNomEquipe(String nomEquipe);
    List<Equipe>findByTournoi(Tournoi tournoi);
}