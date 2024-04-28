package tn.esprit.pi.controllers;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.dto.PaymentInfo;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.services.ICommandeService;
import tn.esprit.pi.services.IPanierAchatService;

import java.util.logging.Logger;


@RestController
@RequestMapping("/commande")
@AllArgsConstructor

public class PanierAchatController {

    // private Logger logger = Logger.getLogger(getClass().getName());
     private IPanierAchatService panierAchatService;
     private ICommandeService commandeService;


    @PostMapping("/add")
    public AchatResponse passerCommande(@RequestBody Achat achat) {

        return panierAchatService.passerCommande(achat);
    }

    @GetMapping("/historique/{email}")
    public Page<Commande> getCommandesByEmail(@PathVariable String email) {
        System.out.println("LA METHODE GETCOMMANDE EST INVOQUE EMAIL: "+email);
        return commandeService.getCommandesByEmail(email);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
    //    logger.info("PaymentInfo.amount: " + paymentInfo.getAmount());
        PaymentIntent paymentIntent = panierAchatService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
