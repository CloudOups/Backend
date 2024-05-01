package tn.esprit.pi.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.pi.entities.Ticket;

public interface ITicketRepository extends CrudRepository<Ticket,Long> {
}
