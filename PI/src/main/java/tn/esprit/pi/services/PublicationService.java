package tn.esprit.pi.services;

import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.IPublicationRepository;

import java.util.List;

public class PublicationService implements IPublicationService {
    IPublicationRepository publicationRepository;


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
}
