package tn.esprit.pi.services;

import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.entities.PanierElement;
import tn.esprit.pi.entities.User;

import java.util.List;

public interface IPanierAchatService {

    AchatResponse passerCommande(Achat achat);
    //   AchatResponse passerCommande2 (User user, Commande commande, List<PanierElement> panierElements);
}
