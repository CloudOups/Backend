package tn.esprit.pi.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeTerrain;
import tn.esprit.pi.services.ITerrainServices;
import tn.esprit.pi.services.TerrainServices;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@RequestMapping("/terrain")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class TerrainRestController {

    private TerrainServices terrainServices;
    // public static String uploadDirectory= System.getProperty("user.dir")+"/src/main/webapp/images";
    // public static String uploadDirectory = System.getProperty("user.dir") + "C:" + File.separator +"xampp" + File.separator + "htdocs" + File.separator + "img" + File.separator + "imgPI";

    @PostMapping(value ="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Terrain addTerrain(
            @RequestParam("nomTerrain") String name,
            @RequestParam("typeTerrain") TypeTerrain type,
            @RequestParam("statusTerrain") StatusTerrain status,
            @RequestParam("imageTerrain") MultipartFile file) {

        Terrain terrain = new Terrain();
        terrain.setNomTerrain(name);
        terrain.setTypeTerrain(type);
        terrain.setStatusTerrain(status);

        return terrainServices.addTerrain(terrain, file);
    }


    //@PostMapping("/add")
//public Terrain addTerrain(@RequestBody Terrain terrain){
    //  return terrainServices.addTerrain(terrain);
//}
    @PutMapping("/update")
    public Terrain updateTerrain(@RequestBody Terrain terrain) {
        return terrainServices.updateTerrain(terrain);
    }
    @GetMapping("/get/{idTerrain}")
    public Terrain getTerrainById(@PathVariable Long idTerrain){
        return terrainServices.getById(idTerrain);
    }
    @DeleteMapping("/delete/{idTerrain}")
    public void removeTerrain(@PathVariable Long idTerrain){
        terrainServices.deleteTerrain(idTerrain);
    }
    @GetMapping("/get/all")
    public List<Terrain> getAll(){ return terrainServices.getAll();
    }
    @GetMapping("/get/status=/{statusTerrain}")
    public List<Terrain> getTerrainByNom(@PathVariable String statusTerrain){
        return terrainServices.getByEtat(statusTerrain);
    }
    @GetMapping("/checkAvailability/datedebut={datedebut}/datefin={datefin}")
    public ResponseEntity<List<Terrain>> checkAvailability(@PathVariable LocalDateTime datedebut, @PathVariable LocalDateTime datefin) {
        List<Terrain> availableTerrains = terrainServices.findAvailableTerrains(datedebut,datefin);
        return ResponseEntity.ok(availableTerrains);
    }
    @GetMapping("/checkAvailabilityBySport/datedebut={datedebut}/datefin={datefin}/{typeTerrain}")
    public ResponseEntity<List<Terrain>> checkAvailabilitybySport(@PathVariable LocalDateTime datedebut, @PathVariable LocalDateTime datefin ,@PathVariable TypeTerrain typeTerrain) {
        List<Terrain> availableTerrainsByType = terrainServices.findAvailableTerrainsByType(datedebut, datefin, typeTerrain);
        return ResponseEntity.ok(availableTerrainsByType);
    }
    @GetMapping("/get/allTerrains")
    public Page<Terrain> getItems(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "sortBy", required = false) String sortBy) {

        Pageable pageable;
        if (sortBy != null) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (sortBy.startsWith("-")) {
                direction = Sort.Direction.DESC;
                sortBy = sortBy.substring(1);
            }
            pageable = PageRequest.of(page, size, direction, sortBy);
        } else {
            pageable = PageRequest.of(page, size);
        }

        return terrainServices.getAllTerrains(pageable);
    }
    @GetMapping("/terrains-by-typeTerrain")
    public Page<Terrain> getReservationsBytypeTerrain(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "size") int size){

        return terrainServices.testerByTypeTerr(page,size);
    }


}