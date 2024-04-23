package tn.esprit.pi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.entities.PanierElement;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PanierAchatService implements IPanierAchatService {

    final IUserRepository userRepository;
    @Override
    @Transactional
    public AchatResponse passerCommande(Achat achat) {
        // recuperer la commande du dto achat
        Commande commande = achat.getCommande();

        // generer un numero de suivi
        String numeroSuivi = genererNumeroSuiviCommande();

        // remplir la commande avec les elements du panier
        Set<PanierElement> panierElements = achat.getPanierElements();
        panierElements.forEach(element -> {
            commande.getPanierItems().add(element);
           // commande.add(element);
        });
        // relier l'utilisateur avec sa commande
            User user = achat.getUser();
            user.getCommandes().add(commande);
        // on MAJ le user avec sa commande
            userRepository.save(user);
        // retouner 'numeroSuivi'
        return new AchatResponse(numeroSuivi);
    }

    private String genererNumeroSuiviCommande() {
        // on va créer un numero de suivi aleatoire et unique
        return UUID.randomUUID().toString();
    }
}
