package tn.esprit.pi.services;

import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.repositories.IReservationTerrRepository;

import java.util.List;

@Service
public class ReservationTerrServices implements IReservationTerrServices{
    IReservationTerrRepository reservationTerrRepository;
    @Override
    public ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain) {
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
}
