package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITournoiRepository;

import java.util.List;

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
        // Vérifier s'il existe déjà une réservation pour la même date et heure
        boolean reservationExistante = reservationTerrRepository.existsByTerrainTypeTerrainAndDateFinBetween(
                tournoi.getTypeTournoi(),tournoi.getDateDebut(), tournoi.getDateFin());


        List<Terrain> terrainsDisponibles = terrainRepository.findTerrainByTypeTerrain(tournoi.getTypeTournoi());
        System.out.println("Terrains disponibles: " + terrainsDisponibles);


        // Filtrer les terrains disponibles qui correspondent au type de tournoi
        List<Terrain> terrainsCorrespondants = terrainsDisponibles.stream()
                .filter(terrain -> terrain.getTypeTerrain().equals(tournoi.getTypeTournoi()) && !reservationExistante)
                .toList();
        System.out.println("Terrains correspondants: " + terrainsCorrespondants);


        if (terrainsCorrespondants.isEmpty()) {
            throw new RuntimeException("Aucun terrain disponible pour le type de tournoi sélectionné.");
        }

        Terrain terrainSelectionne = null;
        int minReservations = Integer.MAX_VALUE;

        // Trouver le terrain qui n'a pas de réservations juste après le tournoi
        for (Terrain terrain : terrainsCorrespondants) {
            boolean hasNoReservationsAfter = terrain.getReservations().stream()
                    .noneMatch(reservation -> reservation.getDateDebut().isAfter(tournoi.getDateFin()));
            System.out.println("Terrain: " + terrain + ", Aucune réservation après: " + hasNoReservationsAfter);
            if (hasNoReservationsAfter) {
                terrainSelectionne = terrain;
                break;
            }
        }

        // Si tous les terrains ont des réservations après le tournoi, choisir celui qui a le moins de réservations ce jour-là
        if (terrainSelectionne == null) {
            for (Terrain terrain : terrainsCorrespondants) {
                int nbReservations = terrain.getReservations().size();
                System.out.println("Terrain: " + terrain + ", Nombre de réservations: " + nbReservations);
                if (nbReservations < minReservations) {
                    terrainSelectionne = terrain;
                    minReservations = nbReservations;
                }
            }
        }

        if (terrainSelectionne == null) {
            throw new RuntimeException("Aucun terrain disponible pour le type de tournoi sélectionné.");
        }

        // Créer une réservation pour le terrain sélectionné pour la durée du tournoi
        ReservationTerrain reservationTerrain = new ReservationTerrain();
        reservationTerrain.setTerrain(terrainSelectionne);
        reservationTerrain.setDateDebut(tournoi.getDateDebut());
        reservationTerrain.setDateFin(tournoi.getDateFin());

        // Sauvegarder la réservation avant de l'associer au tournoi
        reservationTerrain = reservationTerrRepository.save(reservationTerrain);

        // Associer la réservation au tournoi
        tournoi.setReservation(reservationTerrain);
        reservationTerrain.setTournoi(tournoi);
        // Assigner le terrain sélectionné au tournoi
        tournoi.setTerrain(terrainSelectionne);

        // Sauvegarder le tournoi
        return tournoiRepository.save(tournoi);
    }
}


