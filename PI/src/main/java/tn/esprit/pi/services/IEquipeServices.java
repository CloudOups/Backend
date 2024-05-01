package tn.esprit.pi.services;

import tn.esprit.pi.entities.Equipe;

import java.util.List;
import java.util.Optional;

public interface IEquipeServices {
    boolean isUserAlreadyInTeam(Integer id);
    Equipe addEquipe(Equipe equipe,Integer id);
    Equipe updateEquipe(Equipe equipe);
    void delete(Long numequipe);
    Equipe getById(Long numequipe);
    List<Equipe> getAll();
    Equipe getByNom(String nomEquipe);
    Equipe demandeAdhesion(Long idequipe, Integer id);
    Equipe traiterAdhesion(Long idequipe,Integer id,String reponse);

}
