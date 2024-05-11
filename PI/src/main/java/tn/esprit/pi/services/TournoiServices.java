package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITournoiRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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


    public List<Tournoi> getByEvent(Event event) {
        return (List<Tournoi>) tournoiRepository.findByEvent(event);
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
    public Tournoi creerTournoiAutomatique(Tournoi tournoi,Long idevent) {
        Event event = eventRepository.findById(idevent).orElse(null);
        tournoi.setEvent(event);
        event.getTournois().add(tournoi);
        // reserv pour la meme date et heure
        boolean reservationExistante = reservationTerrRepository.existsByTerrainTypeTerrainAndDateFinBetween(
                tournoi.getTypeTournoi(),tournoi.getDateDebut(), tournoi.getDateFin());

        List<Terrain> terrainsDisponibles = terrainRepository.findTerrainByTypeTerrain(tournoi.getTypeTournoi());
        log.info("Terrains disponibles: " + terrainsDisponibles);


        //  terrains disp qui correspondent au type de tournoi
        List<Terrain> terrainsCorrespondants = terrainsDisponibles.stream()
                .filter(terrain -> terrain.getTypeTerrain().equals(tournoi.getTypeTournoi()) && !reservationExistante)
                .toList();
        log.info("qTerrains correspondants: " + terrainsCorrespondants);



        if (terrainsCorrespondants.isEmpty()) {
            log.info("Aucun terrain disponible pour le type de tournoi sélectionné.");
            return null;
        }

        Terrain terrainSelectionne = null;
        int minReservations = Integer.MAX_VALUE;

        // terrain qui n a pas de reserv juste apres tournoi
        for (Terrain terrain : terrainsCorrespondants) {
            boolean hasNoReservationsAfter = terrain.getReservations().stream()
                    .noneMatch(reservation -> reservation.getDateDebut().isAfter(tournoi.getDateFin()));
            log.info("Terrain: " + terrain + ", Aucune réservation après: " + hasNoReservationsAfter);
            if (hasNoReservationsAfter) {
                terrainSelectionne = terrain;
                break;
            }
        }

        // celui qui a le moins de reser
        if (terrainSelectionne == null) {
            for (Terrain terrain : terrainsCorrespondants) {
                int nbReservations = terrain.getReservations().size();
                log.info("Terrain: " + terrain + ", Nombre de réservations: " + nbReservations);
                if (nbReservations < minReservations) {
                    terrainSelectionne = terrain;
                    minReservations = nbReservations;
                }
            }
        }

        if (terrainSelectionne == null) {
            throw new RuntimeException("Aucun terrain disponible pour le type de tournoi sélectionné.");
        }

        ReservationTerrain reservationTerrain = new ReservationTerrain();
        reservationTerrain.setTerrain(terrainSelectionne);
        reservationTerrain.setDateDebut(tournoi.getDateDebut());
        reservationTerrain.setDateFin(tournoi.getDateFin());
        reservationTerrain.setTypeRes(TypeReservation.Tournoi);
        reservationTerrain.setPrixReser(0.0);

        reservationTerrain = reservationTerrRepository.save(reservationTerrain);

        // assign reserv au tournoi
        tournoi.setReservation(reservationTerrain);
        reservationTerrain.setTournoi(tournoi);

        // assign le terrain selectionne au tournoi
        tournoi.getReservation().setTerrain(terrainSelectionne);

        return tournoiRepository.save(tournoi);
    }

    @Override
    public Page<Tournoi> getAllPagination(Pageable pageable) {
        return  tournoiRepository.findAll(pageable);
    }


}