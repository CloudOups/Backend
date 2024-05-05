package tn.esprit.pi.services;

public interface IEventServices {
    Event add(Event event);
    Event update(Event event);
    void delete(Long id);
    Event getById(Long id);
    List<Event> getAll();
    Event findByName(String nom);
    List<Event> recommanderEvenements(Integer userId);
    List<Event> evenementsAvecPlusParticipations();

}
