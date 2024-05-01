package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.services.EquipeServices;
import tn.esprit.pi.services.IEquipeServices;


import java.util.List;

@RequestMapping("/equipe")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")

public class EquipeRestController {

    private IEquipeServices iequipeServices;

    @PostMapping("/add/{id}")
    public Equipe addEquipe(@RequestBody Equipe equipe, @PathVariable Integer id) {
        return iequipeServices.addEquipe(equipe, id);
    }

    @PutMapping("/update")
    public Equipe updateEquipe(@RequestBody Equipe equipe) {
        return iequipeServices.updateEquipe(equipe);
    }

    @GetMapping("/get/{idEquipe}")
    public Equipe getEquipe(@PathVariable Long idEquipe) {
        return iequipeServices.getById(idEquipe);
    }

    @DeleteMapping("/delete/{idEquipe}")
    public void removeEquipe(@PathVariable Long idEquipe) {
        iequipeServices.delete(idEquipe);
    }

    @GetMapping("/get/all")
    public List<Equipe> getAll() {
        return iequipeServices.getAll();
    }

    @PutMapping("/demandeAdhesion/idequipe={idEquipe}/idUser={id}")
    public Equipe demandeEquipe(@PathVariable Long idEquipe, @PathVariable Integer id) {
        return iequipeServices.demandeAdhesion(idEquipe, id);
    }

    @PutMapping("/reponseAdhesion/idequipe={idequipe}/idUser={id}/idreponse={reponse}")
    public Equipe traiterEquipe(@PathVariable Long idequipe, @PathVariable Integer id, @PathVariable String reponse) {
        return iequipeServices.traiterAdhesion(idequipe, id, reponse);
    }
    @GetMapping("/get/nom={nomEquipe}")
    public Equipe getEquipeBynomEquipe(@PathVariable String nomEquipe) {
        return iequipeServices.getByNom(nomEquipe);
    }
}