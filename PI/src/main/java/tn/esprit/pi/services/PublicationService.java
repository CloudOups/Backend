package tn.esprit.pi.services;

import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.IPublicationRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class PublicationService implements IPublicationService {
    IPublicationRepository publicationRepository;
    static long totalPublications = 0;

    @Override
    public Publication addPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public void deletePublication(Long numPub) {
        publicationRepository.deleteById(numPub);
    }

    @Override
    public Publication getById(Long numPub) {
        return publicationRepository.findById(numPub).orElse(null);
    }

    @Override
    public List<Publication> getAll() {
        return publicationRepository.findAll();
    }

    //like publication
    public Publication likePublication(Long numPub) {
        Publication publication = getById(numPub);
        if (publication != null) { //ntestiw publication mawjouda ou non
            publication.setLikes(publication.getLikes() + 1); //incrementiw nb des likes
            return publicationRepository.save(publication); //save puvlication
        }
        return null;
    }

    public Publication unlikePublication(Long numPub) {
        Publication publication = getById(numPub);
        if (publication != null && publication.getLikes() > 0) { //ntestiw publication mawjouda ou non ou zeda nchouf ken les likes > 0
            publication.setLikes(publication.getLikes() - 1);//decrementiw nb des likes
            return publicationRepository.save(publication);//save publication
        }
        return null;
    }
    public void calculeMonthlyPubForStatics() {
        // Get the current month
        Month currentMonth = LocalDate.now().getMonth();
        List<Publication> allPublications = publicationRepository.findAll(); //lehne bech nejbdou publicationet lkol
        long publicationsPostedThisMonth = allPublications.stream()
                .filter(publication -> publication.getDateCreation().getMonth() == currentMonth) //nfiltriw publicationet li 3andhom meme mois
                .count(); //ncomptiw kol publication
        totalPublications += publicationsPostedThisMonth; //incrementiw nombre total ta3 publication

        // n'affichihom fil console bech na3ref S7a7 ou non
        System.out.println("Total publications posted this month: " + publicationsPostedThisMonth);
        System.out.println("Total publications posted overall: " + totalPublications);
    }
}
