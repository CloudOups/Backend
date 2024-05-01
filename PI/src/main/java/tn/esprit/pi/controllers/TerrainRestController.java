package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.services.TerrainServices;

import java.util.List;
@RequestMapping("/terrain")
@AllArgsConstructor
@RestController
public class TerrainRestController {

    private TerrainServices terrainServices;

    @PostMapping("/add")
    public Terrain addTerrain(@RequestBody Terrain terrain) {
        return terrainServices.addTerrain(terrain);
    }
    @PutMapping("/update")
    public Terrain updateTerrain(@RequestBody Terrain terrain) {
        return terrainServices.updateTerrain(terrain);
    }
    @GetMapping("/get/{idTerrain}")
    public Terrain getTerrain(@PathVariable Long idTerrain){
        return terrainServices.getById(idTerrain);
    }
    @DeleteMapping("/delete/{idTerrain}")
    public void removeTerrain(@PathVariable Long idTerrain){
        terrainServices.delete(idTerrain);
    }
    @GetMapping("/get/all")
    public List<Terrain> getAll(){
        return terrainServices.getAll();
    }
}