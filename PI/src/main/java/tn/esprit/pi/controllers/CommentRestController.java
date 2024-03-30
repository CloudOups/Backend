package tn.esprit.pi.controllers;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.CommentService;


import java.util.List;
import java.util.Optional;

@RequestMapping("/commentaire")
@RestController
public class CommentRestController {
    private CommentService  commentaireService;

    @PostMapping("/add")
    public Commentaire addCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.addCommentaire(commentaire);
    }

    @PutMapping("/update")
    public Commentaire updateCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.updateCommentaire(commentaire);
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
