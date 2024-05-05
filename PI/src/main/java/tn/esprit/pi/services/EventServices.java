package tn.esprit.pi.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITerrainRepository;
import tn.esprit.pi.repositories.ITicketRepository;
import tn.esprit.pi.repositories.IUserRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices {

    IEventRepository eventRepository;
    @Autowired
    ITicketRepository ticketRepository;
    IUserRepository userRepository;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";

    @Override
    public Event add(Event event, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            // Construct the file path
            String originalFilename = file.getOriginalFilename();
            String fileName = StringUtils.cleanPath(originalFilename);

            // Save the file to the upload directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory, fileName);
            Files.write(path, bytes);

            Files.createDirectories(path.getParent());

            try (OutputStream os = Files.newOutputStream(path)) {
                os.write(bytes);
            }
            // Set the image file name in terrain
            event.setImage(originalFilename);
            return eventRepository.save(event);
        } catch (IOException e) {
        throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
    }
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


    @Override
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

    //stat
    @Override
    public List<Event> evenementsAvecPlusParticipations() {
        List<Event> tousEvenements = eventRepository.findAll();

        // Trier event par nb de participations decroi
        List<Event> evenementsTries = tousEvenements.stream()
                .map(event -> (Event) event)
                .sorted(Comparator.comparingInt(event -> ((Event) event).getTickets().size()).reversed())
                .collect(Collectors.toList());

        // Retourner les 5 1er event
        return evenementsTries.subList(0, Math.min(5, evenementsTries.size()));
    }

    @Override
    public List<Event> getCompleteEvents() {
        List<Event> completeEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            if(event.getTickets().size()> event.getNbParticipants())
                completeEvents.add(event);
        }
        return completeEvents;

    }

    @Override
    public List<Event> getIncompleteEvents() {
            List<Event> incompleteEvents = new ArrayList<>();
            List<Event> allEvents = eventRepository.findAll();
            for (Event event : allEvents) {
                if (event.getTickets().size() < event.getNbParticipants())
                    incompleteEvents.add(event);
            }
        return incompleteEvents;
    }

    @Override
    public List<Event> getExpiredEvents() {
        List<Event> expiredEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        LocalDateTime currentDate = LocalDateTime.now();
        for (Event event : allEvents) {
            if (event.getDateDebut() != null && event.getDateDebut().isBefore(currentDate)) {
                expiredEvents.add(event);
            }
        }
        return expiredEvents;
    }

    @Override
    public List<Event> getUpcomingEvents() {
        List<Event> upcomingEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        LocalDateTime currentDate = LocalDateTime.now();
        for (Event event : allEvents) {

            if (event.getDateDebut() != null && event.getDateDebut().isAfter(currentDate)) {
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
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
