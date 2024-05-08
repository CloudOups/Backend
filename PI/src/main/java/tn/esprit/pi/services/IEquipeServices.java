package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.pi.entities.Equipe;

import java.util.List;
import java.util.Optional;

public interface IEquipeServices {
    boolean isUserAlreadyInTeam(Integer userId);
    Equipe addEquipe(Equipe equipe,Integer idUser);
    Equipe updateEquipe(Equipe equipe);
    void delete(Long numequipe);
    Equipe getById(Long numequipe);
    List<Equipe> getAll();
    Equipe getByNom(String nomEquipe);
    Equipe demandeAdhesion(Long idequipe, Integer iduser);
    Equipe traiterAdhesion(Long idequipe,Integer userId,String reponse);
    List<Equipe> getEquipeByNumTournoi(Long numTournoi);
    Page<Equipe> getAllPagination(Pageable pageable);
}