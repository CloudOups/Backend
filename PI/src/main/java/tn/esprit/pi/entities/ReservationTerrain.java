package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    LocalDateTime dateDebut;
    LocalDateTime  dateFin;
    @Enumerated(EnumType.STRING)
    TypeReservation typeRes;
    @ManyToOne
    User user;
    @JsonIgnore
    @ManyToOne
    Terrain terrain;
    @JsonIgnore
    @OneToOne
    Tournoi tournoi;
}
