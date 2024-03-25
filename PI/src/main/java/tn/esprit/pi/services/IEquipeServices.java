package tn.esprit.pi.services;

import tn.esprit.pi.entities.Equipe;

import java.util.List;

public interface IEquipeServices {
    Equipe addEquipe(Equipe equipe);
    Equipe updateEquipe(Equipe equipe);
    void delete(int numequipe);
    Equipe getById(int numequipe);
    List<Equipe> getAll();

}
