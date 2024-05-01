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
public class Tournoi  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numTournoi;
    String nomTournoi;
    String Recompense;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tournoi")
    Set<Equipe> equipes;
    @OneToOne(mappedBy = "tournoi")
    ReservationTerrain reservation;


}
