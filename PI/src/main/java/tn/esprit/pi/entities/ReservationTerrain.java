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
    int IdRes;
    LocalDate dateDebut;
    LocalDate dateFin;
    @Enumerated(EnumType.STRING)
    TypeReservation typeRes;
    @ManyToMany(mappedBy = "reservations",cascade = CascadeType.ALL)
    Set<User> users;
    @OneToOne
    Tournoi tournoi;
    @ManyToOne
    Terrain terrain;
}
