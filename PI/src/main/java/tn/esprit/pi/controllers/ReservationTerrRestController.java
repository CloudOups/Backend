package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private ReservationTerrServices reservationTerrServices;
    private EmailController emailController;
    private TerrainServices terrainService;
    private final IReservationTerrRepository iReservationTerrRepository;

    //    @PostMapping("/add/idUser=/{iduser}/idTerrain=/{idTerrain}")
//    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Long iduser,@PathVariable Long idTerrain) {
//        return reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);
//    }
    @PostMapping("/add/idUser=/{iduser}/idTerrain=/{idTerrain}")
    public ReservationTerrain addReservationTerrain(@RequestBody ReservationTerrain reservationTerrain,@PathVariable Integer iduser,@PathVariable Long idTerrain) {
        ReservationTerrain addedReservation = reservationTerrServices.addReservationTerrain(reservationTerrain,iduser,idTerrain);

        // Get user's email address from the reservation or any other source
        String userEmail = addedReservation.getUser().getEmail(); // Assuming user has an email field

        // Compose email content
        String subject = "Reservation Confirmation " + reservationTerrain.getNumRes();
        String message = "Dear " + reservationTerrain.getUser().getFirstname() + ",\n\n" +
                "Thank you for making a reservation for the stadium. Your reservation details are as follows:\n\n" +
                "- Stadium: " + reservationTerrain.getTerrain().getNomTerrain() + "\n" +
                "- Start Date: " + reservationTerrain.getDateDebut() + "\n" +
                "- End Date: " + reservationTerrain.getDateFin() + "\n" +
                "- Total Amount: " + reservationTerrain.getPrixReser() + "\n\n" +
                "We look forward to welcoming you to the stadium. If you have any questions or need further assistance, feel free to contact us.\n\n" +
                "Best regards,\n" ;

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
    public List<ReservationTerrain> getReservationsByUser(@PathVariable Integer userId) {
        return reservationTerrServices.getResByUser(userId);
    }
    @GetMapping("/get/allreservations")
    public Page<ReservationTerrain> getItems(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy) {

        Pageable pageable;
        if (sortBy != null) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (sortBy.startsWith("-")) {
                direction = Sort.Direction.DESC;
                sortBy = sortBy.substring(1);
            }
            pageable = PageRequest.of(page, size, direction, sortBy);
        } else {
            pageable = PageRequest.of(page, size);
        }

        return reservationTerrServices.getAllPagination(pageable);
    }

    @GetMapping("/reservations-by-user")
    public Page<ReservationTerrain> getReservationsByUser(@RequestParam(name = "page") int page,
                                                          @RequestParam(name = "size") int size){

        return reservationTerrServices.testerBYUser(page,size);
    }
    @GetMapping("/reservations-by-datedebut")
    public Page<ReservationTerrain> getReservationsBydatedebut(@RequestParam(name = "page") int page,
                                                               @RequestParam(name = "size") int size){

        return reservationTerrServices.testerByDateDebut(page,size);
    }
    @GetMapping("/reservations-by-datefin")
    public Page<ReservationTerrain> getReservationsBydatefin(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "size") int size){

        return reservationTerrServices.testerByDateFin(page,size);
    }
    @GetMapping("/reservations-by-typeRes")
    public Page<ReservationTerrain> getReservationsBytypeRes(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "size") int size){

        return reservationTerrServices.testerByType(page,size);
    }
    @GetMapping("/reservations-by-nomTerrain")
    public Page<ReservationTerrain> getReservationsByNomTerrain(@RequestParam(name = "page") int page,
                                                                @RequestParam(name = "size") int size){

        return reservationTerrServices.testerByNomTerrain(page,size);
    }
    @GetMapping("/reservations-by-etatRes")
    public Page<ReservationTerrain> getReservationsByEtatRes(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "size") int size){

        return reservationTerrServices.testerByStatus(page,size);
    }
}