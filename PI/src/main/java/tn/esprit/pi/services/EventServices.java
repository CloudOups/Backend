package tn.esprit.pi.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITicketRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices {

    IEventRepository eventRepository;
    @Autowired
    ITicketRepository ticketRepository;
    IUserRepository userRepository;


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
    public Event findByName(String nom) { return eventRepository.findByNomevent(nom); }

    public List<Event> getParticipationHistory(Long userId) {
        List<Ticket> userTickets = ticketRepository.findByUser(userRepository.findByUserId(userId));
        List<Event> participationHistory = new ArrayList<>();
        for (Ticket ticket : userTickets) {
            participationHistory.add(ticket.getEvent());
        }
        return participationHistory;
    }


    public List<Event> recommanderEvenements(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ArrayList<>();
        }
        String userLocation = user.getAdresse();

        List<Event> participationHistory = getParticipationHistory(userId);
        String mostParticipCategory = getMostParticipatedCategory(participationHistory);

        List<Event> recommandations = new ArrayList<>();
        for (Event event : getAll()) {
            if (event.getLocation().equals(userLocation) || event.getCategorie().equals(mostParticipCategory)) {
                recommandations.add(event);
            }
        }
        return recommandations;
    }

    private String getMostParticipatedCategory(List<Event> participationHistory) {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Event event : participationHistory) {
            String category = event.getCategorie();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        String mostParticipatedCategory = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostParticipatedCategory = entry.getKey();
            }
        }
        return mostParticipatedCategory;
    }

//    private String getLastParticipatedCategory(List<Event> participationHistory) {
//        if (!participationHistory.isEmpty()) {
//            Event lastParticipatedEvent = participationHistory.get(participationHistory.size() - 1);
//            return lastParticipatedEvent.getCategorie();
//        }
//        return null;
//    }




}
