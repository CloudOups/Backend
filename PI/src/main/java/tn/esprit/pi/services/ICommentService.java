package tn.esprit.pi.services;

import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Commentaire addCommentaire(Commentaire commentaire);
    Commentaire updateCommentaire(Commentaire commentaire,long id);
    void deleteCommentaire(long idcmt);
    Optional<Commentaire> getCommentaireById(long idcmt);
    List<Commentaire> getAllCommentairesForPublication(Publication publication);
    String analyzeSentiment(String text);
}
