package tn.esprit.pi.services;

import tn.esprit.pi.entities.Abonnement;

import java.util.List;

public interface IAbonnementServices{
     Abonnement updateAbonnement(Abonnement abonnement);
     Abonnement addAbonnement(Abonnement abonnement);
     void delete(Long numAbonn);
     List<Abonnement> getAll();
     Abonnement getById(Long numAbonn);
     void payerAbonnement(Long numAbonn);
      Abonnement assignToSub (Long numAbonn , Long idcours);
     Abonnement getAbonnementUtilisateur(Long userId);
}
