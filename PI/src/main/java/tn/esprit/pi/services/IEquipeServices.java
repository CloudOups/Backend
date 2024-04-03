package tn.esprit.pi.services;

import tn.esprit.pi.entities.Equipe;

import java.util.List;
import java.util.Optional;

public interface IEquipeServices {
    boolean isUserAlreadyInTeam(Long userId);
    Equipe addEquipe(Equipe equipe,Long idUser);
    Equipe updateEquipe(Equipe equipe);
    void delete(Long numequipe);
    Equipe getById(Long numequipe);
    List<Equipe> getAll();
    Equipe getByNom(String nomEquipe);

    Equipe demandeAdhesion(Long idequipe, Long iduser);
    Equipe traiterAdhesion(Long idequipe,Long userId,String reponse);

}
