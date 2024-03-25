package tn.esprit.pi.services;

import tn.esprit.pi.entities.ReservationTerrain;

import java.util.List;

public interface IReservationTerrServices {
    ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain);
    ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain);
    void delete(int numRes);
    ReservationTerrain getById(int numRes);
    List<ReservationTerrain> getAll();
}
