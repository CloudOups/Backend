package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITicketRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TicketServices implements ITicketServices {

    ITicketRepository ticketRepository;
    IEventRepository eventRepository;

    public boolean isEventFull(Long idEvent){
        Event event= eventRepository.findById(idEvent).orElse(null);
        if(event.getTickets().size()> event.getNbParticipants())
            return true;
        return false;
    }
    @Override
    public Ticket addTicket(Ticket ticket,Long idevent) {
        if (isEventFull(idevent)){
            log.info("L'événement est complet, désolé.");
            return null;
        } else {
            Event event = eventRepository.findById(idevent).orElse(null);
            if (event != null) {
                ticket.setEvent(event);
                return ticketRepository.save(ticket);
            } else {
                return null;
            }
        }
    }
    public Ticket createTicket(Event event) {
        if (isEventFull(event.getNumevent())) {
            log.info("L'événement est complet, désolé.");
            return null;
        } else {
            if (event != null) {
                Ticket ticket = new Ticket();
                ticket.setEvent(event);
                ticket.setDateTicket(LocalDate.now());
                //ticket.setUser(user);
                // Assign other necessary details to the ticket
                // Save the ticket to the database
                return ticketRepository.save(ticket);
            }
        else {
            return null;
        }
        }

    }

    @Override
    public Ticket updateTicket(Ticket entity) {
        return ticketRepository.save(entity);
    }
    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket getById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ticket> getAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    EventServices eventServices;

    @Override
    public List<Ticket> getTicketsByEvent(Long idevent) {
        Event event = eventServices.getById(idevent);
        List<Ticket> tickets = new ArrayList<>();
        if (event != null) {
            for (Ticket t : event.getTickets()) {
                tickets.add(t);
            }
        }
        return tickets;
    }


}
