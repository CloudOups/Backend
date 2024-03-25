package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Tournoi extends Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int IdTournoi;
    String nomTournoi;
    String Recompense;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "Tournoi")
    Set<Equipe> equipes;
    @OneToOne(mappedBy = "Tournoi")
    ReservationTerrain reservation;
}
