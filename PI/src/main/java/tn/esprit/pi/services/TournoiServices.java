package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITournoiRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TournoiServices implements InterfaceServices<Tournoi> {

    ITournoiRepository tournoiRepository;
    IEventRepository eventRepository;

    @Override
    public Tournoi add(Tournoi entity) {
        return tournoiRepository.save(entity);
    }
    @Override
    public Tournoi update(Tournoi entity) {
        return tournoiRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        tournoiRepository.deleteById(id);
    }

    @Override
    public Tournoi getById(Long id) {
        return tournoiRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tournoi> getAll() {
        return (List<Tournoi>) tournoiRepository.findAll();
    }
    @Override
    public Tournoi assignToEvent(Long idevent, Long idtournoi) {
        Event event = eventRepository.findById(idevent).orElse(null);
        Tournoi tournoi= tournoiRepository.findById(idtournoi).orElse(null);
        tournoi.setEvent(event);
        return tournoiRepository.save(tournoi);
    }
}
