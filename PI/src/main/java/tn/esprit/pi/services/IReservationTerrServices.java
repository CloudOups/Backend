package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import tn.esprit.pi.entities.ReservationTerrain;

import java.util.List;

public interface IReservationTerrServices {
    ReservationTerrain addReservationTerrain(ReservationTerrain reservationTerrain);
    ReservationTerrain updateReservationTerrain(ReservationTerrain reservationTerrain);
    void delete(Long numRes);
    ReservationTerrain getById(Long numRes);
    List<ReservationTerrain> getAll();
  //  ReservationTerrain assignToUser(Long numRes, Long iduser);
  List< ReservationTerrain> getResByTypeRes(String typeRes);
  List<ReservationTerrain> getResByTerrain(String nomTerrain);
    public Terrain getMostReservedTerrainByType(TypeTerrain typeTerrain);

    Page<ReservationTerrain> getAllPagination(Pageable pageable);
    Page<ReservationTerrain> testerBYUser( int page,int size);

    Page<ReservationTerrain> testerByDateDebut(int page, int size);

    Page<ReservationTerrain> testerByDateFin(int page, int size);

    Page<ReservationTerrain> testerByType(int page, int size);
     Page<ReservationTerrain> testerByNomTerrain(int page, int size) ;

    Page<ReservationTerrain> testerByStatus(int page, int size);
}
