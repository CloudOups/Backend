package tn.esprit.pi.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TerrainServices implements ITerrainServices{
    ITerrainRepository terrainRepository;
    IReservationTerrRepository reservationTerrRepository;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";

    @Override
    public Terrain addTerrain(Terrain terrain, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            // Construct the file path
            String originalFilename = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, originalFilename).toString();

            // Save the file to the upload directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);

            // Set the image file name in terrain
            terrain.setImageTerrain(originalFilename);

            // Save terrain to the repository
            return terrainRepository.save(terrain);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }



@Override
    public Terrain updateTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public void deleteTerrain(Long numTerrain) {
        Terrain terrain = terrainRepository.findById(numTerrain)
                .orElseThrow(() -> new EntityNotFoundException("Terrain not found with id: " + numTerrain));

        boolean hasActiveReservations2=false;
        if (reservationTerrRepository.existsActiveReservationsForTerrain(numTerrain)) {
            hasActiveReservations2=true;
        }
        if (hasActiveReservations2) {
            log.warn("Cannot delete terrain with active reservationssss");
            throw new IllegalStateException("Cannot delete terrain with active reservations");
        }
        else terrainRepository.delete(terrain);
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
    public List<Terrain> getAll() {
        return( List<Terrain>) terrainRepository.findAll();
    }

    @Override
    @Scheduled(cron ="* */30 * * * *")
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

            log.info("Reservation number "+ reservation.getNumRes() +" is expired " );
        }
            else
                log.info("Reservation number "+ reservation.getNumRes() +" is already Expired and treated " );

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
    public List<Terrain> findAvailableTerrainsByType(LocalDateTime startTime, LocalDateTime endTime,TypeTerrain typeTerrain) {
        List<Terrain> availableTerrains = findAvailableTerrains(startTime, endTime);
        // Filter the available terrains by type
        List<Terrain> availableTerrainsByType = availableTerrains.stream()
                .filter(terrain -> terrain.getTypeTerrain().equals(typeTerrain))
                .collect(Collectors.toList());

        return availableTerrainsByType;
    }

}
