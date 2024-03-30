package tn.esprit.pi.services;

import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.IPublicationRepository;

import java.util.List;

public class PublicationService {
    private final IPublicationRepository publicationRepository;


    public PublicationService(IPublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public Publication addPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    public Publication updatePublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    public void deletePublication(Long numPub) {
        publicationRepository.deleteById(numPub);
    }

    public Publication getById(Long numPub) {
        return publicationRepository.findById(numPub).orElse(null);
    }

    public List<Publication> getAll() {
        return publicationRepository.findAll();
    }
}
