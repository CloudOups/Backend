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
}
