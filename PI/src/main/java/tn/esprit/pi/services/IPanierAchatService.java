package tn.esprit.pi.services;

import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;

public interface IPanierAchatService {

    AchatResponse passerCommande(Achat achat);
}
