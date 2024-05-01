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
import tn.esprit.pi.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices {

    IEventRepository eventRepository;
    @Autowired
    ITicketRepository ticketRepository;
    UserRepository userRepository;


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

    public List<Event> getParticipationHistory(Integer userId) {
        List<Ticket> userTickets = ticketRepository.findByUser(userRepository.findById(userId));
        List<Event> participationHistory = new ArrayList<>();
        for (Ticket ticket : userTickets) {
            participationHistory.add(ticket.getEvent());
        }
        return participationHistory;
    }


    @Override
    public List<Event> recommanderEvenements(Integer userId) {
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

    //stat
    @Override
    public List<Event> evenementsAvecPlusParticipations() {
        List<Event> tousEvenements = eventRepository.findAll();
        // Créer une copie de la liste des événements pour ne pas modifier l'original

        // Trier les événements par le nombre de participations décroissant
        List<Event> evenementsTries = tousEvenements.stream()
                .map(event -> (Event) event) // Cast pour spécifier le type d'objet
                .sorted(Comparator.comparingInt(event -> ((Event) event).getTickets().size()).reversed())
                .collect(Collectors.toList());

        // Retourner les premiers n événements avec le plus grand nombre de participations
        return evenementsTries.subList(0, Math.min(5, evenementsTries.size()));
    }



//    @Scheduled(cron = "*/60 * * * * *")
//
//    @Override
//    public void ExpiredEvent() {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date now = new Date();
//        String msgDate = sdf.format(now.getMonth());
//       /* String finalMessage = "";
//        String newLine = System.getProperty("line.separator");*/
//        String s = sdf.format(now);
//
//        List<Event> eventExpiree = this.retrieveAllEvents();
//
//        for (int i = 0; i < eventExpiree.size(); i++) {
//            String a = eventExpiree.get(i).getDateFin().toString();
//
//            if(a == s ) {
//                eventExpiree.get(i).setEtat(1);
//            }
//        }
//
//    }
}
