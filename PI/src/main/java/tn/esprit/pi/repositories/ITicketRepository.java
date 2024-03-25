package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Ticket;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
}
