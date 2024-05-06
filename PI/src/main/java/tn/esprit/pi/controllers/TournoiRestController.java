package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.TypeTerrain;
import tn.esprit.pi.services.EventServices;
import tn.esprit.pi.services.TournoiServices;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/tournoi")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class TournoiRestController {

    private TournoiServices tournoiServices;
    private EventServices eventServices;
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

    @GetMapping("/get/byevent/{eventid}")
    public List<Tournoi> getbyEvent(@PathVariable Long eventId){
        return tournoiServices.getByEvent(eventServices.getById(eventId));
    }


    @PutMapping("/assign/{idevent}/{idtournoi}")
    public Tournoi assignTournoiToEvent(@PathVariable Long idevent,@PathVariable Long idtournoi){
        return tournoiServices.assignToEvent(idevent, idtournoi);
    }

    @PutMapping("/assignTerrain/{idtournoi}/{idterrain}")
    public Tournoi assignTerrainToTournoi(@PathVariable Long idtournoi,@PathVariable Long idterrain){
        return tournoiServices.assignTerrainToTournoi(idtournoi,idterrain);
    }

    @PostMapping("/addtournoireservation/{idevent}")
    public Tournoi creerTournoiAutomatique(@RequestBody Tournoi tournoi, @PathVariable Long idevent) {
        return tournoiServices.creerTournoiAutomatique(tournoi, idevent);
    }

    @GetMapping("/get/withpagination")
    public Page<Tournoi> getItems(@RequestParam(name = "page") int page,
                                  @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tournoiServices.getAllPagination(pageable);
    }

}

