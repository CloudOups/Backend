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
//    @Override
//    public Tournoi assignTerrainToTournoi(Long idtournoi, Long idterrain) {
//        Tournoi tournoi = tournoiRepository.findById(idtournoi).orElse(null);
//        Terrain terrain = terrainRepository.findById(idterrain).orElse(null);
//        tournoi.setTerrain(terrain);
//        return tournoiRepository.save(tournoi);
//    }

    @Override
    public Tournoi creerTournoiAutomatique(Tournoi tournoi, Long idevent) {


        // Vérification de la disponibilité du terrain
        List<ReservationTerrain> reservations = reservationTerrRepository.findReservationsByDateRangeAndTypeTerrain(
                tournoi.getDateDebut(), tournoi.getDateFin(), tournoi.getTypeTournoi());

        if (!reservations.isEmpty()) {
            log.info("Terrain non disponible pour le type de tournoi sélectionné.");
            return null;
        }

        // Recherche d'un terrain disponible
        List<Terrain> terrainsDisponibles = terrainRepository.findTerrainByTypeTerrain(tournoi.getTypeTournoi());
        if(terrainsDisponibles.isEmpty()){
            log.info("pas de terrain disponnible");
            return null;
        }
        Terrain terrainSelectionne = null;

        for (Terrain terrain : terrainsDisponibles) {
            List<ReservationTerrain> reservationsTerrain = reservationTerrRepository.findReservationsByDateRangeAndTerrain(
                    tournoi.getDateDebut(), tournoi.getDateFin(), terrain);

            if (reservationsTerrain.isEmpty()) {
                terrainSelectionne = terrain;
                break;
            }
        }

        if (terrainSelectionne == null) {
            log.info("Aucun terrain disponible pour le type de tournoi sélectionné.");
            return null;
        }

        // Création de la réservation pour le tournoi
        ReservationTerrain reservationTerrain = new ReservationTerrain();
        reservationTerrain.setTerrain(terrainSelectionne);
        reservationTerrain.setDateDebut(tournoi.getDateDebut());
        reservationTerrain.setDateFin(tournoi.getDateFin());
        reservationTerrain.setPrixReser(0.0);

        reservationTerrain = reservationTerrRepository.save(reservationTerrain);
        Event event = eventRepository.findById(idevent).orElse(null);
        tournoi.setEvent(event);
        tournoi.setReservation(reservationTerrain);
        reservationTerrain.setTournoi(tournoi);
        tournoi.setTerrain(terrainSelectionne);

        return tournoiRepository.save(tournoi);
    }

    @Override
    public Page<Tournoi> getAllPagination(Pageable pageable) {
        return  tournoiRepository.findAll(pageable);
    }


}