package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.repositories.ITerrainRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class TerrainServices implements ITerrainServices{
    ITerrainRepository terrainRepository;
    @Override
    public Terrain addTerrain(Terrain terrain) {

        return terrainRepository.save(terrain);
    }

    @Override
    public Terrain updateTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    @Override
    public void delete(Long numterrain) {
terrainRepository.deleteById(numterrain);
    }

    @Override
    public Terrain getById(Long numterrain) {
        return terrainRepository.findById(numterrain).get();
    }

    @Override
    public List<Terrain> getAll() {
        return( List<Terrain>) terrainRepository.findAll();
    }
}
