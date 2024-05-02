package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.*;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor

public class ReservationTerrServices implements IReservationTerrServices {
    IReservationTerrRepository reservationTerrRepository;
    ITerrainRepository terrainRepository;
    IUserRepository userRepository;

    /* @Override
     public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Long idUser,Long idTerrain) {
         Terrain terrain =terrainRepository.findById(idTerrain).orElse(null);
         User user =userRepository.findById(idUser).orElse(null);
         if(user== null){
             log.info("user not found");return null;}
         else if(terrain== null){
             log.info("terrain not found");return null;}
 else {
             reservationTerrain.setTerrain(terrain);
             reservationTerrain.setUser(user);
             return reservationTerrRepository.save(reservationTerrain);
         }
     }*/
    @Override
    public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain, Long idUser, Long idTerrain) {
        Terrain terrain = terrainRepository.findById(idTerrain).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);

        if (user == null) {
            log.warn("User not found");
            return null;
        } else if (terrain == null) {
            log.warn("Terrain not found");
            return null;
        } else {
            // Check if the terrain is available at the time of the new reservation
            if (!isTerrainAvailable(terrain, reservationTerrain)) {
                log.warn("Terrain is already reserved at that time");
                return null;
            }
            double totalPrice = calculateReservationPrice(reservationTerrain.getDateDebut(), reservationTerrain.getDateFin());
            reservationTerrain.setPrixReser(totalPrice);
            terrain.setStatusTerrain(StatusTerrain.valueOf("Reserve"));
            reservationTerrain.setTerrain(terrain);
            reservationTerrain.setUser(user);
            return reservationTerrRepository.save(reservationTerrain);
        }
    }

    public double calculateReservationPrice(LocalDateTime datedebut, LocalDateTime datefin) {
        // Obtenez la durée de la réservation en minutes
        long durationInMinutes = Duration.between(datefin, datedebut).toMinutes();

        // Calculer le prix en fonction de la durée de la réservation
        double pricePerMinute = 0.5; // Prix par minute (à titre d'exemple)
        double totalPrice = durationInMinutes * pricePerMinute;

        return totalPrice;
    }

    private boolean isTerrainAvailable(Terrain terrain, ReservationTerrain newReservation) {
        // Get all reservations for the terrain
        List<ReservationTerrain> reservations = reservationTerrRepository.findByTerrain(terrain);

        // Check if any existing reservation overlaps with the new reservation
        for (ReservationTerrain reservation : reservations) {
            if (reservation.getDateFin().isAfter(newReservation.getDateDebut()) &&
                    newReservation.getDateFin().isAfter(reservation.getDateDebut())) {
                // Les deux intervalles se chevauchent
                return false; // Terrain n'est pas disponible
            }

        }
        return true; // Terrain is available
    }

    @Override
    public ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain) {
        //    if(reservationTerrain.getEtatReser()==true &&)
        return reservationTerrRepository.save(reservationTerrain);
    }

    @Override
    public void delete(Long numRes) {
        reservationTerrRepository.deleteById(numRes);

    }

    @Override
    public ReservationTerrain getById(Long numRes) {
        return reservationTerrRepository.findById(numRes).get();
    }

    @Override
    public List<ReservationTerrain> findReservationByEtat(boolean etat) {
        List<ReservationTerrain> result = reservationTerrRepository.findReserByEtatReser(etat);
        return result;
    }


    @Override
    public List<ReservationTerrain> getAll() {
        return (List<ReservationTerrain>) reservationTerrRepository.findAll();
    }

    @Override
    public List<ReservationTerrain> getResByTypeRes(String typeRes) {
        try {
            TypeReservation type = TypeReservation.valueOf(typeRes);
            List<ReservationTerrain> result = reservationTerrRepository.findByTypeRes(type);
            return result;
        } catch (IllegalArgumentException e) {
            log.warn("Invalid type of reservation: " + typeRes);
            return null;
        }
    }

    @Override

    public List<ReservationTerrain> getResByTerrain(String nomTerrain) {
        Terrain terrain = terrainRepository.findByNomTerrain(nomTerrain);
        if (terrain != null) {
            List<ReservationTerrain> result = reservationTerrRepository.findByTerrain(terrain);
            return result;
        }
        return Collections.emptyList();
    }
    public List<ReservationTerrain> getResByUser(Long userId) {
        List<ReservationTerrain> reservationsByUser = reservationTerrRepository.findByUser_UserId(userId);
        if (reservationsByUser != null) {
            return reservationsByUser;
        }
        return Collections.emptyList();
    }

    @Override
    public Terrain getMostReservedTerrainByType(TypeTerrain typeTerrain) {
        List<Terrain> terrainsOfType = terrainRepository.findByTypeTerrain(typeTerrain);
        if (terrainsOfType.isEmpty()) {
            log.warn("No terrains of type {} found", typeTerrain);
            return null;
        }

        Optional<Terrain> mostReservedTerrain = terrainsOfType.stream()
                .max(Comparator.comparingInt(terrain -> reservationTerrRepository.findByTerrain(terrain).size()));
        return mostReservedTerrain.orElse(null);
    }
    @Override
    public Page<ReservationTerrain> getAllPagination(Pageable pageable){
        return  reservationTerrRepository.findAll(pageable);
    }
    @Override
    public Page<ReservationTerrain> testerBYUser(int page, int size) {
        Sort sort=Sort.by("user").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByDateDebut(int page, int size) {
        Sort sort=Sort.by("dateDebut").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByDateFin(int page, int size) {
        Sort sort=Sort.by("dateFin").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByType(int page, int size) {
        Sort sort=Sort.by("typeRes").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByNomTerrain(int page, int size) {
        Sort sort=Sort.by("terrain").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
    @Override
    public Page<ReservationTerrain> testerByStatus(int page, int size) {
        Sort sort=Sort.by("etatReser").ascending();
        PageRequest pageable = PageRequest.of(page, size,sort); // Page 1 avec 10 éléments par page, ajustez selon vos besoins
        return reservationTerrRepository.findAll( pageable);    }
}


