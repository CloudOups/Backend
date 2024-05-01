package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numevent;
    String nomevent;
    String categorie;
    String location;

    LocalDateTime dateDebut;
    LocalDateTime dateFin;
    String image;
    int nbParticipants;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    Set<Ticket> tickets;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
    Set<Tournoi> tournois;
}
