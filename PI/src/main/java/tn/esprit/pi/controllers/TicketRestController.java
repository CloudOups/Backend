package tn.esprit.pi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.services.TicketServices;

import java.util.List;

@RequestMapping("/ticket")
@AllArgsConstructor
@RestController
public class TicketRestController {
    private TicketServices ticketServices;

    @PostMapping("/add")
    public Ticket addTicket(@RequestBody Ticket ticket){
        return  ticketServices.add(ticket);
    }

    @PutMapping("/update")
    public Ticket updateTicket(@RequestBody Ticket ticket){
        return ticketServices.update(ticket);
    }

    @DeleteMapping("/delete/{idticket}")
    public void deleteTicket(@PathVariable Long idticket){
        ticketServices.delete(idticket);
    }

    @GetMapping("/get/{idticket}")
    public Ticket getTicketById(@PathVariable Long idticket){
        return ticketServices.getById(idticket);
    }

    @GetMapping("/get/all")
    public List<Ticket> getAll(){
        return ticketServices.getAll();
    }
}


