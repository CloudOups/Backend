package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.services.EquipeServices;


import java.util.List;

@RequestMapping("/equipe")
@AllArgsConstructor
@RestController
public class EquipeRestController {

    private EquipeServices equipeServices;

    @PostMapping("/add/{iduser}")
    public Equipe addEquipe(@RequestBody Equipe equipe, @PathVariable Long iduser) {
        return equipeServices.addEquipe(equipe,iduser);
    }
    @PutMapping("/update")
    public Equipe updateEquipe(@RequestBody Equipe equipe) {
        return equipeServices.updateEquipe(equipe);
    }
    @GetMapping("/get/{idEquipe}")
    public Equipe getEquipe(@PathVariable Long idEquipe){
        return equipeServices.getById(idEquipe);
    }
    @DeleteMapping("/delete/{idEquipe}")
    public void removeEquipe(@PathVariable Long idEquipe){
         equipeServices.delete(idEquipe);
    }
    @GetMapping("/get/all")
    public List<Equipe>  getAll(){
        return equipeServices.getAll();
    }
    @PutMapping("/demandeAdhesion/{idEquipe}/{idUser}")
    public  Equipe demandeEquipe(@PathVariable Long idEquipe ,@PathVariable Long idUser){
        return equipeServices.demandeAdhesion(idEquipe,idUser);
    }
    @PutMapping("/reponseAdhesion/{idequipe}/{idUser}/{reponse}")
    public  Equipe reponseEquipe(@PathVariable Long idequipe ,@PathVariable Long idUser, @PathVariable String reponse){
        return equipeServices.reponseAdhesion(idequipe,idUser,reponse);
    }
}