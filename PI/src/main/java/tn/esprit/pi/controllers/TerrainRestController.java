package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.services.TerrainServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@RequestMapping("/terrain")
@AllArgsConstructor
@RestController
public class TerrainRestController {

    private TerrainServices terrainServices;
    public static String uploadDirectory= System.getProperty("user.dir")+"/src/main/webapp/images";
   // public static String uploadDirectory = System.getProperty("user.dir") + "C:" + File.separator +"xampp" + File.separator + "htdocs" + File.separator + "img" + File.separator + "imgPI";

    @PostMapping("/add")
    public Terrain addTerrain(@ModelAttribute Terrain terrain, @RequestParam("image") MultipartFile file) throws IOException
    {String OriginalFilename= file.getOriginalFilename();
        Path fileNameAndPath= Paths.get(uploadDirectory,OriginalFilename);
        Files.write(fileNameAndPath,file.getBytes());
        terrain.setImageTerrain(OriginalFilename);
        return terrainServices.addTerrain(terrain);
    }
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
        terrainServices.delete(idTerrain);
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
}