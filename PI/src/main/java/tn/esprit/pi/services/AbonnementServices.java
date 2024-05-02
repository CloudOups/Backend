package tn.esprit.pi.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.entities.CoursSport;
import tn.esprit.pi.repositories.IAbonnementRepository;
import tn.esprit.pi.repositories.ICoursSportRepository;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Service
public class AbonnementServices implements IAbonnementServices {
    IAbonnementRepository iAbonnementRepository;
    ICoursSportRepository iCoursSportRepository;
    @Override

    public Abonnement updateAbonnement(Abonnement abonnement) {return iAbonnementRepository.save(abonnement);}
    @Override

    public Abonnement addAbonnement(Abonnement abonnement) { return iAbonnementRepository.save(abonnement); }
    @Override

    public void delete(Long numAbonn) { iAbonnementRepository.deleteById(numAbonn);}
    @Override

    public List<Abonnement> getAll() {return (List<Abonnement>) iAbonnementRepository.findAll();}
    @Override
    public Abonnement getById(Long numAbonn) { return iAbonnementRepository.findById(numAbonn).orElse(null);}


    @Override
    public Abonnement getAbonnementUtilisateur(Long userId) {return iAbonnementRepository.findById(userId).orElse(null);
    }

    public Abonnement assignToSub (Long numAbonn, Long idcours){
    Abonnement abonnement = iAbonnementRepository.findById(numAbonn).orElse(null);
    CoursSport coursSport = iCoursSportRepository.findById(idcours).orElse(null);
    abonnement.setCoursSport(coursSport);
    return iAbonnementRepository.save(abonnement);
    }
    @Override
    public void payerAbonnement(Long numAbonn) {
        Abonnement abonnement = iAbonnementRepository.findById(numAbonn).orElse(null);
        if (abonnement != null) {
            abonnement.setPaye(true);
            iAbonnementRepository.save(abonnement);
        } else {
            envoyerNotificationDateExpiration(numAbonn);
        }
    }

    private void envoyerNotificationDateExpiration(Long numAbonn) {
        System.out.println("Envoyer une notification à l'utilisateur pour informer que la date" +
                " de paiement est expirée pour l'abonnement avec l'identifiant : " + numAbonn);
    }



}
