package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Event;
import tn.esprit.pi.entities.Ticket;
import tn.esprit.pi.entities.Tournoi;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.repositories.IEventRepository;
import tn.esprit.pi.repositories.ITicketRepository;
import tn.esprit.pi.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class TicketServices implements ITicketServices {

    ITicketRepository ticketRepository;
    IEventRepository eventRepository;
    UserRepository userRepository;
    EmailQRcodeService emailServices;

    public boolean isEventFull(Long idEvent){
        Event event= eventRepository.findById(idEvent).orElse(null);
        if(event.getTickets().size()> event.getNbParticipants())
            return true;
        return false;
    }
    @Override
    public Ticket addTicket(Ticket ticket,Long idevent) {
        if (isEventFull(idevent)){
            log.info("L'événement est complet, désolé.");
            return null;
        } else {
            Event event = eventRepository.findById(idevent).orElse(null);
            if (event != null) {
                ticket.setEvent(event);
                return ticketRepository.save(ticket);
            } else {

                return null;
            }
        }
    }

    @Override
    public Ticket createTicket(Event event, User user) {
        if (event == null) {
            log.error("L'événement est null.");
            return null;
        }

        if (isEventFull(event.getNumevent())) {
            log.info("L'événement est complet, désolé.");
            return null;
        }

        boolean userHasTicketForEvent = userHasTicketForEvent(user, event);
        if (userHasTicketForEvent) {
            log.info("L'utilisateur a déjà un ticket pour cet événement.");
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setDateTicket(LocalDate.now());
        ticket.setUser(user);
        sendEmailWithTicketDetails(ticket);

        return ticketRepository.save(ticket);
    }

    public boolean userHasTicketForEvent(User user, Event event) {
        Set<Ticket> userTickets = user.getTickets();
        for (Ticket ticket : userTickets) {
            if (ticket.getEvent().equals(event)) {
                return true;
            }
        }
        return false;
    }


    private void sendEmailWithTicketDetails(Ticket ticket) {
        String qrCodeDetails = "Nomevent: " + ticket.getEvent().getNomevent() + "\n"
                +  ticket.getEvent().getImage() + "\n"
                + "Date debut: " + ticket.getEvent().getDateDebut() + "\n"
                + "L'emplacement: " + ticket.getEvent().getLocation() + "\n"
                + "Firstname: " + ticket.getUser().getFirstname() + "\n"
                + "Lastname: " + ticket.getUser().getLastname();

        // Envoyer l'e-mail avec le QR code contenant les détails de l'événement
        String subject = "Votre ticket pour l'événement : " + ticket.getEvent().getNomevent();
        String message = "Bonjour " + ticket.getUser().getFirstname() + ",\n"
                + "Vous avez réservé un ticket pour l'événement " + ticket.getEvent().getNomevent() + ".\n"
                + "Veuillez trouver ci-joint votre ticket contenant les détails de l'événement.";

        emailServices.sendEmailWithQRCode(subject, message, ticket.getUser().getEmail(), qrCodeDetails);
    }

    @Override
    public Ticket updateTicket(Ticket entity) {
        return ticketRepository.save(entity);
    }
    @Override
    public void deleteTicket(Long id) {
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

    EventServices eventServices;


    @Override
    public List<Ticket> getTicketsByEvent(Long idevent) {
        Event event = eventServices.getById(idevent);
        List<Ticket> tickets = new ArrayList<>();
        if (event != null) {
            for (Ticket t : event.getTickets()) {
                tickets.add(t);
            }
        }
        return tickets;
    }


    @Override
    public Page<Ticket> getAllPagination(Pageable pageable) {
        return  ticketRepository.findAll(pageable);
    }
}
