package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITournoiRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TournoiServices implements ITournoiServices{

    ITournoiRepository tournoiRepository;
    IEventRepository eventRepository;
    ITerrainRepository terrainRepository;


    @Override
    public Tournoi add(Tournoi entity) {
        return tournoiRepository.save(entity);
    }
    @Override
    public Tournoi update(Tournoi entity) {
        return tournoiRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        tournoiRepository.deleteById(id);
    }

    @Override
    public Tournoi getById(Long id) {
        return tournoiRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tournoi> getAll() {
        return (List<Tournoi>) tournoiRepository.findAll();
    }
    @Override
    public Tournoi assignToEvent(Long idevent, Long idtournoi) {
        Event event = eventRepository.findById(idevent).orElse(null);
        Tournoi tournoi= tournoiRepository.findById(idtournoi).orElse(null);
        tournoi.setEvent(event);
        return tournoiRepository.save(tournoi);
    }

    @Override
    public Tournoi assignTerrainToTournoi(Long idtournoi, Long idterrain) {
        Tournoi tournoi = tournoiRepository.findById(idtournoi).orElse(null);
        Terrain terrain = terrainRepository.findById(idterrain).orElse(null);
        tournoi.setTerrain(terrain);
        return tournoiRepository.save(tournoi);
    }

}
