package tn.esprit.pi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.repositories.ICommandeRepository;


@Service
@RequiredArgsConstructor
public class CommandeService implements ICommandeService {

    final private ICommandeRepository commandeRepository;
    @Override
    public Page<Commande> getCommandesByEmail(String email) {
        return commandeRepository.findCommandeByUserEmailOrderByDateCreationDesc(email, null);
    }
}
