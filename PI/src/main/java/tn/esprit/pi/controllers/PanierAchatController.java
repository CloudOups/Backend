package tn.esprit.pi.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.repositories.ICommandeRepository;
import tn.esprit.pi.services.ICommandeService;
import tn.esprit.pi.services.IPanierAchatService;


@RestController
@RequestMapping("/commande")
@RequiredArgsConstructor
public class PanierAchatController {

    final private IPanierAchatService panierAchatService;
    final ICommandeService commandeService;


    @PostMapping("/add")
    public AchatResponse passerCommande(@RequestBody Achat achat) {

        return panierAchatService.passerCommande(achat);
    }

    @GetMapping("/historique/{email}")
    public Page<Commande> getCommandesByEmail(@PathVariable String email) {
        System.out.println("LA METHODE GETCOMMANDE EST INVOQUE EMAIL: "+email);
        return commandeService.getCommandesByEmail(email);
    }



}
