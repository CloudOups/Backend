package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.repositories.ITicketRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServices implements InterfaceServices<Ticket> {

    ITicketRepository ticketRepository;
    @Override
    public Ticket add(Ticket entity) {
        return ticketRepository.save(entity);
    }
    @Override
    public Ticket update(Ticket entity) {
        return ticketRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
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
}
