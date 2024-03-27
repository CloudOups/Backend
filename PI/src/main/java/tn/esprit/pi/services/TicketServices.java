package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITicketRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServices implements ITicketServices {

    ITicketRepository ticketRepository;
    IEventRepository eventRepository;

    public boolean isEventFull(Long idEvent){
        Event event= eventRepository.findById(idEvent).orElse(null);
        if(event.getTickets().size()< event.getNbParticipants())
            return true;
        return false;
    }
    @Override
    public Ticket addTicket(Ticket ticket,Long idevent) {
        if (isEventFull(idevent))
            return ticketRepository.save(ticket);
        return null;
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
    public List<Ticket> getTicketsByEvent(String nomevent) {
        Event event = eventServices.findByName(nomevent);
        List<Ticket> tickets = new ArrayList<>();
        if (event != null) {
            for (Ticket t : event.getTickets()) {
                tickets.add(t);
            }
        }
        return tickets;
    }



//    @Override
//    public Ticket assignToEvent(Long idevent, Long idticket) {
//        Event event = eventRepository.findById(idevent).orElse(null);
//        Ticket ticket= ticketRepository.findById(idticket).orElse(null);
//        ticket.setEvent(event);
//        return ticketRepository.save(ticket);
//    }

}
