package tn.esprit.pi.services;

import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pi.entities.Commentaire;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.ICommentRepository;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.util.Properties;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {

     @Autowired
     ICommentRepository commentaireRepository;

  //  private final StanfordCoreNLP pipeline;




    @Override
    public Commentaire addCommentaire(Commentaire commentaire) {
        String sentiment = analyzeSentiment(commentaire.getContenucm());
        commentaire.setSentiment(sentiment);
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
                sentiment = "Happy"; // Positive or Very Positive
            } else {
                sentiment = "Angry"; // Negative, Very Negative, or Neutral
            }
            return sentiment;
        }*/
        return "Neutral"; // Default to Neutral if sentiment cannot be determined
    }

}
