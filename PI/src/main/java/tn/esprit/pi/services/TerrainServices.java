package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.repositories.ITerrainRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Service
@AllArgsConstructor
public class TerrainServices implements ITerrainServices{
    ITerrainRepository terrainRepository;
    @Override
    public Terrain addTerrain(Terrain terrain) {

        try {
            // Construct the file path
            String originalFilename = file.getOriginalFilename();
            String fileName = StringUtils.cleanPath(originalFilename);

            // Save the file to the upload directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory,fileName);
            Files.write(path, bytes);

            Files.createDirectories(path.getParent());

            try (OutputStream os = Files.newOutputStream(path)) {
                os.write(bytes);
            }
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
    @Override
    public Page<Terrain> getAllTerrains(Pageable pageable) {
        return terrainRepository.findAll(pageable);
    }

    @Override
    public Page<Terrain> testerByTypeTerr(int page, int size) {
        Sort sort=Sort.by("typeTerrain").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return terrainRepository.findAll( pageable);    }



}


