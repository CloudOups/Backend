package tn.esprit.pi.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.services.ReservationTerrServices;

import java.util.List;

public class ReservationTerrRestController {
    private ReservationTerrServices reservationTerrServices;

    @PostMapping("/add")
    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain) {
        return reservationTerrServices.addReservationTerrain(reservationTerrain);
    }
    @PutMapping("/update")
    public ReservationTerrain updateReservationTerrain(@RequestBody ReservationTerrain reservationTerrain) {
        return reservationTerrServices.updateReservationTerrain(reservationTerrain);
    }
    @GetMapping("/get/{idReservationTerrain}")
    public ReservationTerrain getReservationTerrain(@PathVariable Long idReservationTerrain){
        return reservationTerrServices.getById(idReservationTerrain);
    }
    @DeleteMapping("/delete/{idReservationTerrain}")
    public void removeReservationTerrain(@PathVariable Long idReservationTerrain){
        reservationTerrServices.delete(idReservationTerrain);
    }
    @GetMapping("/get/all")
    public List<ReservationTerrain> getAll(){
        return reservationTerrServices.getAll();
    }
}
