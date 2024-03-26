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

public class ReservationTerrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numRes;
    LocalDate dateDebut;
    LocalDate dateFin;
    @Enumerated(EnumType.STRING)
    TypeReservation typeRes;
    @ManyToOne
    User user;

    @ManyToOne
    Terrain terrain;
    @OneToOne
    Tournoi tournoi;

}
