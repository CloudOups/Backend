package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.User;

import java.util.List;

public interface ITicketServices {
    Ticket addTicket(Ticket ticket,Long idevent);
    Ticket createTicket(Event event, User user);

    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Long numticket);
    Ticket getById(Long numticket);
    List<Ticket> getAll();
//    List<Ticket> getTicketsByEvent(String nomevent);
    List<Ticket> getTicketsByEvent(Long idevent);
    Page<Ticket> getAllPagination(Pageable pageable);


}
