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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

@RequestMapping("/ticket")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")

public class TicketRestController {
    private TicketServices ticketServices;
    private EventServices eventService;



    UserServiceImp userService;
    @PostMapping("/participate/{eventId}")
    public Ticket participateEvent(@PathVariable Long eventId, Principal principal) {
        Event event = eventService.getById(eventId);
        User user = userService.getCurrentUser(principal);
        return ticketServices.createTicket(event, user);
    }

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

    @GetMapping("/get/withpagination")
    public Page<Ticket> getItems(@RequestParam(name = "page") int page,
                                  @RequestParam(name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ticketServices.getAllPagination(pageable);
    }

//    @PutMapping("/assign/{idevent}/{idticket}")
//    public Ticket assignTicketToEvent(@PathVariable Long idevent, @PathVariable Long idticket){
//        return ticketServices.assignToEvent(idevent, idticket);
//    }
}


