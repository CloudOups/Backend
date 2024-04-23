package tn.esprit.pi.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.services.IPanierAchatService;

@RestController
@RequestMapping("/panier")
@RequiredArgsConstructor
public class PanierAchatController {

    final private IPanierAchatService panierAchatService;

    @PostMapping("/passerCommande")
    public AchatResponse passerCommande(@RequestBody Achat achat) {
        return panierAchatService.passerCommande(achat);
    }
}
