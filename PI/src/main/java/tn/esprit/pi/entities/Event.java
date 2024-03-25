package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
    int eventid;
    String nomevent;
    LocalDate dateDebut;
    LocalDate dateFin;
    String image;
    int nbPlace;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Event")
    Set<Ticket> tickets;
}
