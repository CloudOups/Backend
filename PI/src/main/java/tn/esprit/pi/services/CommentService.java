package tn.esprit.pi.services;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.ICommentRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {

     @Autowired
     ICommentRepository commentaireRepository;


    @Override
    public Commentaire addCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public Commentaire updateCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public void deleteCommentaire(long idcmt) {
        commentaireRepository.deleteById(idcmt);

    }

    @Override
    public Optional<Commentaire> getCommentaireById(long idcmt) {
        return commentaireRepository.findById(idcmt);
    }

    @Override
    public List<Commentaire> getAllCommentairesForPublication(Publication publication) {
        return commentaireRepository.findByPublication(publication);
    }
}
