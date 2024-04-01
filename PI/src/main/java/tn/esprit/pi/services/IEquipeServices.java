package tn.esprit.pi.services;

import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.User;

import java.util.List;

public interface IEquipeServices {
    boolean isUserAlreadyInTeam(Long userId);
    Equipe addEquipe(Equipe equipe,Long idUser);
    Equipe updateEquipe(Equipe equipe);
    void delete(Long numequipe);
    Equipe getById(Long numequipe);
    List<Equipe> getAll();
    Equipe demandeAdhesion(Long idequipe, Long iduser);
    Equipe traiterAdhesion(Long idequipe,Long userId,String reponse);

}
