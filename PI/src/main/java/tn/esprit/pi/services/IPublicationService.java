package tn.esprit.pi.services;

import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface IPublicationService {
    Publication addPublication(Publication publication);
    Publication updatePublication(Publication publication,long id);
    void deletePublication(Long numPub);
    Publication getById(Long numPub);
    List<Publication> getAll();
    List<Publication> getAllApprovedPublications();

    List<Publication> getAllUnapprovedPublications();

}
