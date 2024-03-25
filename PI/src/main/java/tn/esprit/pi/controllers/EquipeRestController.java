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

    @PostMapping("/add")
    public Equipe addEquipe(@RequestBody Equipe equipe) {
        return equipeServices.addEquipe(equipe);
    }
    @PutMapping("/update")
    public Equipe updateEquipe(@RequestBody Equipe equipe) {
        return equipeServices.updateEquipe(equipe);
    }
    @GetMapping("/get/{{idEquipe}}")
    public Equipe getEquipe(@PathVariable int idEquipe){
        return equipeServices.getById(idEquipe);
    }
    @DeleteMapping("/delete/{{idEquipe}}")
    public void removeEquipe(@PathVariable int idEquipe){
         equipeServices.delete(idEquipe);
    }
    @GetMapping("/get/all")
    public List<Equipe>  getAll(){
        return equipeServices.getAll();
    }
}