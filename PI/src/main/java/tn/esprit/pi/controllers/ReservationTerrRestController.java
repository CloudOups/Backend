package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.services.ReservationTerrServices;

import java.util.List;
@RequestMapping("/reservation")
@AllArgsConstructor
@RestController
public class ReservationTerrRestController {
    private ReservationTerrServices reservationTerrServices;

    @PostMapping("/add/{iduser}/{idTerrain}")
    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Long iduser,@PathVariable Long idTerrain) {
        return reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);
    }
    @PutMapping("/update")
    public ReservationTerrain updateReservationTerrain(@RequestBody ReservationTerrain reservationTerrain) {
        return reservationTerrServices.updateReservationTerrain(reservationTerrain);
    }
    @GetMapping("/get/{idRes}")
    public ReservationTerrain getReservationTerrain(@PathVariable Long idRes){
        return reservationTerrServices.getById(idRes);
    }
    @DeleteMapping("/delete/{idRes}")
    public void removeReservationTerrain(@PathVariable Long idRes){
        reservationTerrServices.delete(idRes);
    }
    @GetMapping("/get/all")
    public List<ReservationTerrain> getAll(){
        return reservationTerrServices.getAll();
    }

    @PutMapping("/assignToUser/{numRes}/{iduser}")
    public  ReservationTerrain assignToUser(@PathVariable Long numRes, @PathVariable Long iduser){
        return reservationTerrServices.assignToUser(numRes,iduser);
    }
}
