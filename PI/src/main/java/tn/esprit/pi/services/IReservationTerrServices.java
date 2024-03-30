package tn.esprit.pi.services;

import tn.esprit.pi.entities.ReservationTerrain;

import java.util.List;

public interface IReservationTerrServices {
    ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Long idUser,Long idTerrain);
    ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain);
    void delete(Long numRes);
    ReservationTerrain getById(Long numRes);
    List<ReservationTerrain> getAll();
  //  ReservationTerrain assignToUser(Long numRes, Long iduser);
}
