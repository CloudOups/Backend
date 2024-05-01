package tn.esprit.pi.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.ICommentRepository;


import java.io.Console;
import java.util.Properties;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tn.esprit.pi.repositories.IPublicationRepository;
@Slf4j

@Service
public class CommentService implements ICommentService {


     @Autowired
     ICommentRepository commentaireRepository;
    @Autowired
    IPublicationRepository publicationRepository;
  //  private final StanfordCoreNLP pipeline;




    @Override
    public Commentaire addCommentaire(Commentaire commentaire, long publicationId) {
        String sentiment = analyzeSentiment(commentaire.getContenucm());
        commentaire.setSentiment(sentiment);

        // Fetch the publication from the database using its ID
        Publication publication = publicationRepository.findById(publicationId).orElse(null);
        if (publication != null) {
            commentaire.setPublication(publication);
        } else {

        }

        return commentaireRepository.save(commentaire);
    }

    @Override
    public Commentaire updateCommentaire(Commentaire c, long id) {
        Commentaire commentaire=null;
        c=commentaireRepository.findById(id).orElse(null);
        commentaire.setContenucm(c.getContenucm());
        commentaire.setDatecm(c.getDatecm());
        commentaire.setSentiment(c.getSentiment());
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

    @Override
    public String analyzeSentiment(String text) {
        /*Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            int sentimentValue = RNNCoreAnnotations.getPredictedClass(tree);
            String sentiment;
            if (sentimentValue == 2 || sentimentValue == 3) {
                sentiment = "Happy";
            } else {
                sentiment = "Angry";
            }
            return sentiment;
        }*/
        return "Neutral"; // Default to Neutral if sentiment cannot be determined
    }

}
