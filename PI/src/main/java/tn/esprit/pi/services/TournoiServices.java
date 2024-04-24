package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITournoiRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TournoiServices implements ITournoiServices{

    @Autowired
    ITournoiRepository tournoiRepository;
    IEventRepository eventRepository;
    @Autowired
    ITerrainRepository terrainRepository;
    @Autowired
    IReservationTerrRepository reservationTerrRepository;


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

    @Override
    public Tournoi creerTournoiAutomatique(Tournoi tournoi) {
        boolean reservationExistante = reservationTerrRepository.existsByTerrainTypeTerrainAndDateFinBetween(
                tournoi.getTypeTournoi(), tournoi.getDateDebut(), tournoi.getDateFin());

        List<Terrain> terrainsDisponibles = terrainRepository.findTerrainByTypeTerrain(tournoi.getTypeTournoi());

        List<Terrain> terrainsCorrespondants = terrainsDisponibles.stream()
                .filter(terrain -> !reservationExistante ||
                        terrain.getReservations().stream()
                                .noneMatch(reservation -> reservation.getDateDebut().isBefore(tournoi.getDateFin()) &&
                                        reservation.getDateFin().isAfter(tournoi.getDateDebut())))
                .collect(Collectors.toList());

        if (terrainsCorrespondants.isEmpty()) {
            throw new RuntimeException("Aucun terrain disponible pour le type de tournoi sélectionné.");
        }

        Terrain terrainSelectionne = terrainsCorrespondants.stream()
                .min(Comparator.comparingInt(terrain -> terrain.getReservations().size()))
                .orElseThrow(() -> new RuntimeException("Erreur lors de la sélection du terrain."));

        ReservationTerrain reservationTerrain = new ReservationTerrain();
        reservationTerrain.setTerrain(terrainSelectionne);
        reservationTerrain.setDateDebut(tournoi.getDateDebut());
        reservationTerrain.setDateFin(tournoi.getDateFin());
        reservationTerrain = reservationTerrRepository.save(reservationTerrain);

        tournoi.setReservation(reservationTerrain);
        tournoi.setTerrain(terrainSelectionne);
        return tournoiRepository.save(tournoi);
    }

}


