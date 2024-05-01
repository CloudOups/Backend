package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
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
    Double PrixReser;
    //@JsonIgnore
    @ManyToOne
    User user;
    //@JsonIgnore
    @ManyToOne
    Terrain terrain;
    @OneToOne
    Tournoi tournoi;

}
