package tn.esprit.pi.services;

import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeTerrain;

import java.util.List;

public interface IReservationTerrServices {
    ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain,Integer idUser,Long idTerrain);
    ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain);
    void delete(Long numRes);
    ReservationTerrain getById(Long numRes);
    List<ReservationTerrain>findReservationByEtat(boolean etat);
    List<ReservationTerrain> getAll();
  //  ReservationTerrain assignToUser(Long numRes, Long iduser);
  List< ReservationTerrain> getResByTypeRes(String typeRes);
  List<ReservationTerrain> getResByTerrain(String nomTerrain);
    public Terrain getMostReservedTerrainByType(TypeTerrain typeTerrain);
}
