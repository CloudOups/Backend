package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class ReservationTerrServices implements IReservationTerrServices{
    IReservationTerrRepository reservationTerrRepository;
    ITerrainRepository terrainRepository;
    IUserRepository userRepository;
    @Override
    public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Long idUser,Long idTerrain) {
        Terrain terrain =terrainRepository.findById(idTerrain).orElse(null);
        User user =userRepository.findById(idUser).orElse(null);
        reservationTerrain.setTerrain(terrain);
        reservationTerrain.setUser(user);
        return reservationTerrRepository.save(reservationTerrain);
    }

    @Override
    public ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain) {
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
    public List<ReservationTerrain> getAll() {
        return (List<ReservationTerrain>) reservationTerrRepository.findAll();
    }

    @Override
    public ReservationTerrain assignToUser(Long numRes, Long iduser) {
        ReservationTerrain reservationTerrain =reservationTerrRepository.findById(numRes).orElse(null) ;
        User user =userRepository.findById(iduser).orElse(null);
        reservationTerrain.setUser(user);
        return reservationTerrRepository.save(reservationTerrain);
    }
}

