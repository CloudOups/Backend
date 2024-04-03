package tn.esprit.pi.services;

import tn.esprit.pi.entities.ReservationTerrain;

import java.util.List;

public interface IReservationTerrServices {
    ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Long idUser,Long idTerrain);
    ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain);
    void delete(Long numRes);
    ReservationTerrain getById(Long numRes);
    List<ReservationTerrain>findReservationByEtat(boolean etat);
    List<ReservationTerrain> getAll();
  //  ReservationTerrain assignToUser(Long numRes, Long iduser);
  List< ReservationTerrain> getResByTypeRes(String typeRes);
  List<ReservationTerrain> getResByTerrain(String nomTerrain);
}
