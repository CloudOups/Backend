package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.services.EventServices;
import tn.esprit.pi.services.TicketServices;
import tn.esprit.pi.services.UserServiceImp;

import java.security.Principal;
import java.util.List;

@RequestMapping("/ticket")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")

public class TicketRestController {
    private TicketServices ticketServices;
    private EventServices eventService;

//    @PostMapping("/participate/{eventId}/{userId}")
//    public Ticket participateEvent(@PathVariable Long eventId, @PathVariable Long userId) {
//        Event event = eventService.getById(eventId);
//        User user = userService.getById(userId);
//        return ticketService.createTicket(event, user);
//    }

    UserServiceImp userService;
    @PostMapping("/participate/{eventId}")
    public Ticket participateEvent(@PathVariable Long eventId, Principal principal) {
        Event event = eventService.getById(eventId);
        User user = userService.getCurrentUser(principal); // Obtenir l'utilisateur connecté
        return ticketServices.createTicket(event, user); // Passer l'utilisateur à la méthode de création de ticket
    }

//    @PostMapping("/add/{idevent}")
//    public Ticket addTicket(@RequestBody Ticket ticket,@PathVariable Long idevent){
//        return  ticketServices.addTicket(ticket,idevent);
//    }
    @PutMapping("/update")
    public Ticket updateTicket(@RequestBody Ticket ticket){
        return ticketServices.updateTicket(ticket);
    }

    @DeleteMapping("/delete/{idticket}")
    public void deleteTicket(@PathVariable Long idticket){
        ticketServices.deleteTicket(idticket);
    }

    @GetMapping("/get/{idticket}")
    public Ticket getTicketById(@PathVariable Long idticket){
        return ticketServices.getById(idticket);
    }

    @GetMapping("/getByEvent/{idevent}")
    public List<Ticket> getTicketsByEvent(@PathVariable Long idevent) {
        return ticketServices.getTicketsByEvent(idevent);
    }

    @GetMapping("/get/all")
    public List<Ticket> getAll(){
        return ticketServices.getAll();
    }

//    @PutMapping("/assign/{idevent}/{idticket}")
//    public Ticket assignTicketToEvent(@PathVariable Long idevent, @PathVariable Long idticket){
//        return ticketServices.assignToEvent(idevent, idticket);
//    }
}


