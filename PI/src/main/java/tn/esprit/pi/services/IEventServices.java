package tn.esprit.pi.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Event;

import java.util.List;

public interface IEventServices {

    Event add(Event event, MultipartFile file);
    Event update(Event event);
    void delete(Long id);
    Event getById(Long id);
    List<Event> getAll();
    Event findByName(String nom);
    List<Event> recommanderEvenements(Integer userId);
    List<Event> evenementsAvecPlusParticipations();
    List<Event> getCompleteEvents();
    List<Event> getIncompleteEvents();
    List<Event> getExpiredEvents();
    List<Event> getUpcomingEvents();


}