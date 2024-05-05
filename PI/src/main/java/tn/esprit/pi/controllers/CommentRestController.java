package tn.esprit.pi.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.CommentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/commentaire")
@RestController
public class CommentRestController {
    @Autowired
    private  CommentService commentaireService;



    @PostMapping("/addcommentaire/{publicationId}")
    public Commentaire addCommentaire(@PathVariable long publicationId,@RequestBody Commentaire commentaire) {
        commentaire.setDatecm(new Date());
        return commentaireService.addCommentaire(commentaire,publicationId);
    }

    @PutMapping("/updatecommentaire/{id}")
    public Commentaire updateCommentaire(@RequestBody Commentaire commentaire, @PathVariable long id) {
        return commentaireService.updateCommentaire(commentaire,id);
    }

    @DeleteMapping("/delete/{idcmt}")
    public void deleteCommentaire(@PathVariable long idcmt) {
        commentaireService.deleteCommentaire(idcmt);
    }

    @GetMapping("/get/{idcmt}")
    public Optional<Commentaire> getCommentaireById(@PathVariable long idcmt) {
        return commentaireService.getCommentaireById(idcmt);
    }

    @GetMapping("/get/byPublication/{publicationId}")
    public List<Commentaire> getAllCommentairesForPublication(@PathVariable long publicationId) {
        Publication publication = new Publication();
        publication.setNumPub(publicationId);
        return commentaireService.getAllCommentairesForPublication(publication);
    }
}