package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Ticket> tickets;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Tournoi> tournois;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(numevent, event.numevent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numevent);
    }

}
