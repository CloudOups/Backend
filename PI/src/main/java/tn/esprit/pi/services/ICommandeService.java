package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.pi.entities.Commande;


public interface ICommandeService {

    public Page<Commande> getCommandesByEmail(String email);
}
