package tn.esprit.pi.services;

import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;

import java.security.Principal;
import java.util.List;

public interface ITicketServices {

    Ticket addTicket(Ticket ticket,Long idevent);
    Ticket createTicket(Event event);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Long numticket);
    Ticket getById(Long numticket);
    List<Ticket> getAll();
//    List<Ticket> getTicketsByEvent(String nomevent);
    List<Ticket> getTicketsByEvent(Long idevent);


}
