package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Abonnement;
import tn.esprit.pi.services.IAbonnementServices;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/abonnement")
@RestController
public class AbonnementRestController {

    private IAbonnementServices abonnementServices;

    @PostMapping ("/add")
    public Abonnement addAbonnement(@RequestBody Abonnement abonnement){return  abonnementServices.addAbonnement(abonnement);}


    @PutMapping ("/update")
    public Abonnement updateAbonnement(@RequestBody Abonnement abonnement){return  abonnementServices.updateAbonnement(abonnement);}
    @GetMapping ("/get/{numAbonn}")
    public Abonnement getpiste(@PathVariable Long numAbonn){return  abonnementServices.getById(numAbonn);}
    @DeleteMapping("/delete/{numAbonn}")
    public void removePiste(@PathVariable Long numAbonn){ abonnementServices.delete(numAbonn);}

    @GetMapping("/all")
    public List<Abonnement> getAll(){return abonnementServices.getAll();}


}

