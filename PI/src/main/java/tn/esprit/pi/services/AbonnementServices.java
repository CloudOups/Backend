package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.repositories.IAbonnementRepository;

import java.util.List;


@AllArgsConstructor
@Service
public class AbonnementServices implements IAbonnementServices {
    IAbonnementRepository abonnementRepository;

    public Abonnement updateAbonnement(Abonnement abonnement) {return abonnementRepository.save(abonnement);}

    public Abonnement addAbonnement(Abonnement abonnement) { return abonnementRepository.save(abonnement); }

    public void delete(Long numAbonn) { abonnementRepository.deleteById(numAbonn);}

    public List<Abonnement> getAll() {return (List<Abonnement>) abonnementRepository.findAll();}
    public Abonnement getById(Long numAbonn) { return abonnementRepository.findById(numAbonn).orElse(null);}
}
