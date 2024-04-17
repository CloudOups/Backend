package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.User;

import java.util.List;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    //Ticket getTicketByEvent(String nomEvent);
    List<Ticket> findByUser(User user);
}
