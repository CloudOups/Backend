package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.EmailRequest;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.entities.TypeTerrain;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.services.ReservationTerrServices;
import tn.esprit.pi.services.TerrainServices;

import java.time.LocalDateTime;
import java.util.List;
@RequestMapping("/reservation")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")

public class ReservationTerrRestController {
    private ReservationTerrServices reservationTerrServices;
    private EmailController emailController;
    private TerrainServices terrainService;
    private final IReservationTerrRepository iReservationTerrRepository;

//    @PostMapping("/add/idUser=/{iduser}/idTerrain=/{idTerrain}")
//    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Long iduser,@PathVariable Long idTerrain) {
//        return reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);
//    }
@PostMapping("/add/idUser=/{iduser}/idTerrain=/{idTerrain}")
public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Long iduser,@PathVariable Long idTerrain) {
    ReservationTerrain addedReservation = reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);

    // Get user's email address from the reservation or any other source
    String userEmail = addedReservation.getUser().getEmail(); // Assuming user has an email field

    // Compose email content
    String subject = "Reservation Confirmation "+reservationTerrain.getNumRes() ;
    String message = "Your reservation has been successfully added !" +
            "You have"+reservationTerrain.getPrixReser()+" to pay";

    // Send email
    EmailRequest emailRequest = new EmailRequest(userEmail, subject, message);
    ResponseEntity<?> emailResponse = emailController.sendEmail(emailRequest);

    // Handle email sending response if needed

    return addedReservation;
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

    @GetMapping("/calculateReservationPrice/datedebut={datedebut}/datefin={datefin}")
    public Double calculateReservationPrice(@PathVariable("datedebut") LocalDateTime datedebut, @PathVariable("datefin") LocalDateTime datefin) {

        double totalPrice = reservationTerrServices.calculateReservationPrice(datedebut,datefin);

        return totalPrice;
    }

    @GetMapping("/most-reserved-terrain/{typeTerrain}")
    public Terrain getMostReservedTerrain(@PathVariable TypeTerrain typeTerrain) {
        return reservationTerrServices.getMostReservedTerrainByType(typeTerrain);
    }

    @GetMapping("/get/ReservationbyUserId/{userId}")
    public List<ReservationTerrain> getReservationsByUser(@PathVariable Long userId) {
        return reservationTerrServices.getResByUser(userId);
    }
}
