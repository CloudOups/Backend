package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.User;

import java.util.List;

public interface IEquipeServices {
    Equipe addEquipe(Equipe equipe,Long idUser);
    Equipe updateEquipe(Equipe equipe);
    void delete(Long numequipe);
    Equipe getById(Long numequipe);
    List<Equipe> getAll();
    Equipe demandeAdhesion(Long idequipe, Long iduser);
    Equipe reponseAdhesion(Long idequipe,Long userId,String reponse);

    Page<Equipe> getAllPagination(Pageable pageable);
}
