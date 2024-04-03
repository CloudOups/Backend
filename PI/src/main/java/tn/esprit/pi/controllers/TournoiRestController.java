package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.services.TournoiServices;

import java.util.List;

@RequestMapping("/tournoi")
@AllArgsConstructor
@RestController
public class TournoiRestController {

    private TournoiServices tournoiServices;
    @PostMapping("/add")
    public Tournoi addTournoi(@RequestBody Tournoi tournoi){
        return  tournoiServices.add(tournoi);
    }

    @PutMapping("/update")
    public Tournoi updateTournoi(@RequestBody Tournoi tournoi){
        return tournoiServices.update(tournoi);
    }

    @DeleteMapping("/delete/{idtournoi}")
    public void deleteTournoi(@PathVariable Long idtournoi){
        tournoiServices.delete(idtournoi);
    }

    @GetMapping("/get/{idtournoi}")
    public Tournoi getTournoiById(@PathVariable Long idtournoi){
        return tournoiServices.getById(idtournoi);
    }

    @GetMapping("/get/all")
    public List<Tournoi> getAll(){
        return tournoiServices.getAll();
    }

    @PutMapping("/assign/{idevent}/{idtournoi}")
    public Tournoi assignTournoiToEvent(@PathVariable Long idevent,@PathVariable Long idtournoi){
        return tournoiServices.assignToEvent(idevent, idtournoi);
    }

    @PutMapping("/assignTerrain/{idtournoi}/{idterrain}")
    public Tournoi assignTerrainToTournoi(@PathVariable Long idtournoi,@PathVariable Long idterrain){
        return tournoiServices.assignTerrainToTournoi(idtournoi,idterrain);
    }
}


