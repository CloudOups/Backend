package tn.esprit.pi.services;

import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.TypeTerrain;

import java.time.LocalDate;
import java.util.List;

public interface ITournoiServices {
    Tournoi add(Tournoi tournoi);
    Tournoi update(Tournoi tournoi);
    void delete(Long id);
    Tournoi getById(Long id);
    List<Tournoi> getAll();
    Tournoi assignToEvent(Long idevent,Long idtournoi);
    Tournoi assignTerrainToTournoi(Long tournoiId, Long terrainId);
    Tournoi creerTournoiAutomatique(Tournoi tournoi);
}
