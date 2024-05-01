package tn.esprit.pi.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;

import java.util.Collection;
import java.util.List;

public interface ITerrainServices {
    Terrain addTerrain(Terrain terrain, MultipartFile file);
    Terrain updateTerrain(Terrain terrain);
    void deleteTerrain(Long numterrain);
    Terrain getById(Long numterrain);
    List<Terrain> getByEtat(String statusTerrain);
    List<Terrain> getAll();
    void getExpiredRes();

}
