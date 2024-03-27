package tn.esprit.pi.services;

import tn.esprit.pi.entities.Ticket;

import java.util.List;

public interface ITicketServices {
    Ticket addTicket(Ticket ticket,Long idevent);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Long numticket);
    Ticket getById(Long numticket);
    List<Ticket> getAll();
    List<Ticket> getTicketsByEvent(String nomevent);

}
