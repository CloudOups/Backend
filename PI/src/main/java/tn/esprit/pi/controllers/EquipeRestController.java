package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.services.EquipeServices;


import java.util.List;

@RequestMapping("/equipe")
@AllArgsConstructor
@RestController
public class EquipeRestController {

    private EquipeServices equipeServices;

    @PostMapping("/add/{iduser}")
    public Equipe addEquipe(@RequestBody Equipe equipe, @PathVariable Integer iduser) {
        return iequipeServices.addEquipe(equipe, iduser);
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

    @PutMapping("/demandeAdhesion/idequipe={idEquipe}/idUser={idUser}")
    public Equipe demandeEquipe(@PathVariable Long idEquipe, @PathVariable Integer idUser) {
        return iequipeServices.demandeAdhesion(idEquipe, idUser);
    }

    @PutMapping("/reponseAdhesion/idequipe={idequipe}/idUser={idUser}/idreponse={reponse}")
    public Equipe traiterEquipe(@PathVariable Long idequipe, @PathVariable Integer idUser, @PathVariable String reponse) {
        return iequipeServices.traiterAdhesion(idequipe, idUser, reponse);
    }
    @GetMapping("/get/allEquipes")
    public Page<Equipe> getItems(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iequipeServices.getAllPagination(pageable);
    }
}