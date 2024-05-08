package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Equipe;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.ITournoiRepository;
import tn.esprit.pi.services.EquipeServices;
import tn.esprit.pi.services.IEquipeServices;
import tn.esprit.pi.services.UserServiceImp;


import java.security.Principal;
import java.util.List;

@RequestMapping("/equipe")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")

public class EquipeRestController {
    UserServiceImp userService;

    private IEquipeServices iequipeServices;
    private final ITournoiRepository iTournoiRepository;

    @PostMapping("/add/{iduser}")
    public Equipe addEquipe(@RequestBody Equipe equipe, @PathVariable Integer iduser) {
        return iequipeServices.addEquipe(equipe, iduser);
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

    /*@PutMapping("/demandeAdhesion/idequipe={idEquipe}/idUser={idUser}")
    public Equipe demandeEquipe(@PathVariable Long idEquipe, @PathVariable Integer idUser) {
        return iequipeServices.demandeAdhesion(idEquipe, idUser);
    }*/
    @PutMapping("/demandeAdhesion/idequipe={idEquipe}")
    public Equipe demandeEquipe(@PathVariable Long idEquipe, Principal principal) {
        User user = userService.getCurrentUser(principal);

        return iequipeServices.demandeAdhesion(idEquipe,user.getId());
    }

    @PutMapping("/reponseAdhesion/idequipe={idequipe}/idUser={idUser}/idreponse={reponse}")
    public Equipe traiterEquipe(@PathVariable Long idequipe, @PathVariable Integer idUser, @PathVariable String reponse) {
        return iequipeServices.traiterAdhesion(idequipe, idUser, reponse);
    }
    @GetMapping("/get/nom={nomEquipe}")
    public Equipe getEquipeBynomEquipe(@PathVariable String nomEquipe) {
        return iequipeServices.getByNom(nomEquipe);
    }
    @GetMapping("/get/allEquipes")
    public Page<Equipe> getItems(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iequipeServices.getAllPagination(pageable);
    }
    @GetMapping("/get/numTournoi/{numTournoi}")
    public List<Equipe> getEquipeByNumTournoi(@PathVariable Long numTournoi) {

        return iequipeServices.getEquipeByNumTournoi(numTournoi);
    }
}

