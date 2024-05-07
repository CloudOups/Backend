package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.services.EventServices;
import tn.esprit.pi.services.UserServiceImp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")

public class EventRestController {
    EventServices eventServices;



    @PostMapping(value= "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Event addEvent(@ModelAttribute Event event, @RequestParam("img")MultipartFile imgg)
    {

        return eventServices.add(event, imgg);
    }


    @PutMapping("/update")
    public Event updateEvent(@RequestBody Event event){
        return eventServices.update(event);
    }

    @DeleteMapping("/delete/{idEvent}")
    public void deleteEvent(@PathVariable Long idEvent){
        eventServices.delete(idEvent);
    }

    @GetMapping("/get/{idEvent}")
    public Event getEventById(@PathVariable Long idEvent){
        return eventServices.getById(idEvent);
    }

    @GetMapping("/get/all")
    public List<Event> getAll(){
        return eventServices.getAll();
    }

    @GetMapping("/get/mostparticipation")
    public List<Event> evenementsAvecPlusParticipations(){
        return eventServices.evenementsAvecPlusParticipations();
    }

    @GetMapping("/get/complete")
    public List<Event> getCompleteEvents() {
        return eventServices.getCompleteEvents();
    }

    @GetMapping("/get/incomplete")
    public List<Event> getIncompleteEvents() {
        return eventServices.getIncompleteEvents();
    }

    @GetMapping("/get/expired")
    public List<Event> getExpiredEvents() {
        return eventServices.getExpiredEvents();
    }

    @GetMapping("/get/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventServices.getUpcomingEvents();
    }

    UserServiceImp userService;

//    @GetMapping("/get/history")
//    public List<Event> getParticipationHistory(Principal principal){
//        User user = userService.getCurrentUser(principal);
//        return  eventServices.getParticipationHistory(user.getId());
//
//    }

    @GetMapping("/get/history")
    public ResponseEntity<List<Event>> getParticipationHistory(Principal principal){
        User user = userService.getCurrentUser(principal);
        List<Event> participationHistory = eventServices.getParticipationHistory(user.getId()); // Assurez-vous que le nom de votre service est correct
        return new ResponseEntity<>(participationHistory, HttpStatus.OK);
    }


}
