package tn.esprit.pi.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.repositories.IEventRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices {
    IEventRepository eventRepository;
    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getAll() {
        return (List<Event>) eventRepository.findAll() ;
    }

    @Override
    public Event findByName(String nom) {
        List<Event> allEvents = getAll();
        for (Event e : allEvents) {
            if (e.getNomevent().equalsIgnoreCase(nom)) {
                return e;
            }
        }
        return null;
    }

}
