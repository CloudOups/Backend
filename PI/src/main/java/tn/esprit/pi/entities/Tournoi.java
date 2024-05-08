package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class  Tournoi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numTournoi;
    String nomTournoi;
    LocalDateTime dateDebut,dateFin;
    TypeTerrain typeTournoi;
    String Recompense;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tournoi")
    Set<Equipe> equipes;
    @OneToOne(mappedBy = "tournoi")
    ReservationTerrain reservation;
    @JsonIgnore
    @ManyToOne
    Event event;
    @JsonIgnore
    @ManyToOne
    Terrain terrain;
}
