package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.services.IAbonnementServices;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/abonnement")
@RestController
public class AbonnementRestController {

    private IAbonnementServices iabonnementServices;

    @PostMapping ("/add")
    public Abonnement addAbonnement(@RequestBody Abonnement abonnement){return  iabonnementServices.addAbonnement(abonnement);}


    @PutMapping ("/update")
    public Abonnement updateAbonnement(@RequestBody Abonnement abonnement){return  iabonnementServices.updateAbonnement(abonnement);}
    @PutMapping("/update/{numAbonn}")
    public Abonnement updateAbonnement(@RequestBody Abonnement abonnement,@PathVariable long numAbonn){
        return iabonnementServices.updateAbonnement(abonnement);}
    @GetMapping ("/get/{numAbonn}")
    public Abonnement getpiste(@PathVariable Long numAbonn){return  iabonnementServices.getById(numAbonn);}
    @DeleteMapping("/delete/{numAbonn}")
    public void removePiste(@PathVariable Long numAbonn){ iabonnementServices.delete(numAbonn);}

    @GetMapping("/all")
    public List<Abonnement> getAll(){return iabonnementServices.getAll();}

    @GetMapping("/abonnements/{userId}")
    public Abonnement getAbonnementUtilisateur(@PathVariable Long userId) {

        return iabonnementServices.getAbonnementUtilisateur(userId);
    }

    @PutMapping ("/assignToSub/{numAbonn}/{idcours}")
    public Abonnement assignToSub(@PathVariable Long numAbonn, @PathVariable Long idcours){
        return iabonnementServices.assignToSub(numAbonn , idcours);
    }

    @PostMapping("/payer/{numAbonn}")
    public String payerAbonnement(@PathVariable Long numAbonn) { iabonnementServices.payerAbonnement(numAbonn);
        return "L'abonnement avec l'identifiant " + numAbonn + " a été payé avec succès.";
    }

}

