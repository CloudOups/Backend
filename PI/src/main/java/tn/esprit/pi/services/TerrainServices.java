package tn.esprit.pi.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@AllArgsConstructor
public class TerrainServices implements ITerrainServices{
    ITerrainRepository terrainRepository;
    IReservationTerrRepository reservationTerrRepository;
    @Override
    public Terrain addTerrain(Terrain terrain) {return terrainRepository.save(terrain);
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
    public List<Terrain> getByEtat(String statusTerrain){
        StatusTerrain status = StatusTerrain.valueOf(statusTerrain);
        List<Terrain> result = terrainRepository.findByStatusTerrain(status);
        return result;
    };

    @Override
    public List<Terrain> getAll() {return( List<Terrain>) terrainRepository.findAll();
    }

    @Override
    @Scheduled(cron ="*/30 * * * * *")
    public void getExpiredRes() {
        List<ReservationTerrain> expiredReservations = reservationTerrRepository.findByExpired();

        for (ReservationTerrain reservation : expiredReservations) {
            // Update reservation state to false
            if (reservation.getEtatReser()){
            reservation.setEtatReser(false);
            reservationTerrRepository.save(reservation);

            // Free up the associated terrain
            Terrain terrain = reservation.getTerrain();
            terrain.setStatusTerrain(StatusTerrain.Libre);
            terrainRepository.save(terrain);

            System.out.println("Reservation number "+ reservation.getNumRes() +" is expired " );
        }
            else
                System.out.println("Reservation number "+ reservation.getNumRes() +" is already Expired and treated " );

        }}


    public List<Terrain> findAvailableTerrains(LocalDateTime startTime, LocalDateTime endTime) {
        // Récupérer tous les terrains avec des réservations qui se chevauchent avec la période spécifiée
        List<Terrain> availableTerrains = ( List<Terrain>)terrainRepository. findAll();

        // Filtrer les terrains disponibles
        List<Terrain> filteredTerrains = new ArrayList<>();
        for (Terrain terrain : availableTerrains) {
            // Vérifier s'il y a des réservations pour ce terrain
            if (terrain.getReservations().isEmpty()) {
                filteredTerrains.add(terrain);
            } else {
                boolean isAvailable = true;
                // Vérifier si le terrain est disponible pour les horaires spécifiés
                for (ReservationTerrain reservation : terrain.getReservations()) {
                    if (reservation.getDateDebut().isBefore(endTime) && reservation.getDateFin().isAfter(startTime)) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    filteredTerrains.add(terrain);
                }
            }
        }
        return filteredTerrains;
    }

    public void confirmReservation(Long terrainId, LocalDateTime startTime, LocalDateTime endTime, User user) {
        Terrain terrain = terrainRepository.findById(terrainId).orElseThrow(() -> new EntityNotFoundException("Terrain not found"));

        // Créer la réservation
        ReservationTerrain reservation = new ReservationTerrain();
        reservation.setDateDebut(startTime);
        reservation.setDateFin(endTime);
        reservation.setTerrain(terrain);
        reservation.setUser(user);

        // Enregistrer la réservation
        reservationTerrRepository.save(reservation);
    }
}
