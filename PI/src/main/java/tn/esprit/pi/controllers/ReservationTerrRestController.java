package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.services.ReservationTerrServices;

import java.util.List;
@RequestMapping("/reservation")
@AllArgsConstructor
@RestController
public class ReservationTerrRestController {
    private ReservationTerrServices reservationTerrServices;
    private final IReservationTerrRepository iReservationTerrRepository;

    @PostMapping("/add/idUser=/{iduser}/idTerrain=/{idTerrain}")
    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Long iduser,@PathVariable Long idTerrain) {
        return reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);
    }
    @PutMapping("/update")
    public ReservationTerrain updateReservationTerrain(@RequestBody ReservationTerrain reservationTerrain) {
        return reservationTerrServices.updateReservationTerrain(reservationTerrain);
    }
    @GetMapping("/get/idRes=/{idRes}")
    public ReservationTerrain getReservationTerrainbyId(@PathVariable Long idRes){
        return reservationTerrServices.getById(idRes);
    }
    @DeleteMapping("/delete/idRes=/{idRes}")
    public void removeReservationTerrain(@PathVariable Long idRes){
        reservationTerrServices.delete(idRes);
    }
    @GetMapping("/get/all")
    public List<ReservationTerrain> getAll(){
        return reservationTerrServices.getAll();
    }
    @GetMapping("/get/Res/etat={etat}")
    public List<ReservationTerrain> getAllTypePrets(@PathVariable boolean etat) {
        return reservationTerrServices.findReservationByEtat(etat);
    }
    @GetMapping("/get/typeRes=/{typeRes}")
    public List<ReservationTerrain> getReservationbyTypeRes(@PathVariable String typeRes){
        return reservationTerrServices.getResByTypeRes(typeRes);
    }
    @GetMapping("/get/nomTerrain=/{nomTerrain}")
    public List<ReservationTerrain> getReservationbyNomTerrain(@PathVariable String nomTerrain){
        return reservationTerrServices.getResByTerrain(nomTerrain);
    }


}
