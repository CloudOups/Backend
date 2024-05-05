package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;

import java.util.List;

public interface ITerrainServices {
    Terrain addTerrain(Terrain terrain);
    Terrain updateTerrain(Terrain terrain);
    void delete(Long numterrain);
    Terrain getById(Long numterrain);
    List<Terrain> getAll();
    void getExpiredRes();

    Page<Terrain> getAllTerrains(Pageable pageable);
    Page<Terrain> testerByTypeTerr(int page, int size);

}
