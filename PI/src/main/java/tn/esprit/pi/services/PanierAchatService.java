package tn.esprit.pi.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.pi.dto.Achat;
import tn.esprit.pi.dto.AchatResponse;
import tn.esprit.pi.dto.PaymentInfo;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.entities.PanierElement;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Service

public class PanierAchatService implements IPanierAchatService {

    IUserRepository userRepository;
    public PanierAchatService(IUserRepository userRepository,
                              @Value("${stripe.secret.key}") String secretKey) {

        this.userRepository = userRepository;

        //initialisation de la clé secret de stripe
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public AchatResponse passerCommande(Achat achat) {
        // recuperer la commande du dto achat
        Commande commande = achat.getCommande();

        // generer un numero de suivi
        String numeroSuivi = genererNumeroSuiviCommande();
        commande.setNumeroSuiviCommande(numeroSuivi);

        // remplir la commande avec les elements du panier
        Set<PanierElement> panierElements = achat.getCommandeElements();
        if(!achat.getCommandeElements().isEmpty()){
            panierElements.forEach(element -> {
                //commande.getPanierItems().add(element);
                commande.add(element);
            });
        }

        // relier l'utilisateur avec sa commande
        User utilisateur = achat.getUtilisateur();

       // commande.setEmail(utilisateur.getEmail());

        //verifier si l'utilisateur existe deja
        String email = utilisateur.getEmail();

        User utilisateurExistant = userRepository.findByEmail(email);

        if(utilisateurExistant != null){
            // si l'utilisateur existe deja, on le recupere pour l'utiliser
            utilisateur = utilisateurExistant;
        }

        utilisateur.addCommande(commande);


        // on MAJ le user avec sa commande
        userRepository.save(utilisateur);

        // retouner 'numeroSuivi'
        return new AchatResponse(numeroSuivi);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentinfo) throws StripeException {
        // on va creer un payment intent avec le montant et la devise
        List<String> paymentMethodTypes = List.of("card");

        // on va creer un map avec les parametres
        Map<String, Object> params = Map.of(
                "amount", paymentinfo.getAmount(),
                "currency", paymentinfo.getCurrency(),
                "payment_method_types", paymentMethodTypes,
                "receipt_email",paymentinfo.getReceiptEmail(),
                "description", "Achat de produits gérer par le groupe CLOUDOPS pour le PI ESPRIT"
        );
        return PaymentIntent.create(params);
    }


    private String genererNumeroSuiviCommande() {
        // on va créer un numero de suivi aleatoire et unique
        return UUID.randomUUID().toString();
    }
}
