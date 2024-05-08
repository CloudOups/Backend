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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class ReservationTerrain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numRes;
    Boolean etatReser =true;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime  dateFin;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime dateRes;
    @Enumerated(EnumType.STRING)
    TypeReservation typeRes;
    String codePromo =null;
    Double PrixReser;
    //@JsonIgnore
    @ManyToOne
    User user;
    //@JsonIgnore
    @ManyToOne
    Terrain terrain;
    @JsonIgnore
    @OneToOne
    Tournoi tournoi;

}
