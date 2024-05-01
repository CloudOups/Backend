package tn.esprit.pi.services;

import tn.esprit.pi.entities.Event;

import java.util.List;

public interface IEventServices {
    Event add(Event event);
    Event update(Event event);
    void delete(Long id);
    Event getById(Long id);
    List<Event> getAll();
    Event findByName(String nom);
    List<Event> recommanderEvenements(Long userId);
    List<Event> evenementsAvecPlusParticipations();

}