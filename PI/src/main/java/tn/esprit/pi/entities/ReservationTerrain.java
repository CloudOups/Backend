package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.time.LocalDate;
<<<<<<< HEAD
import java.time.LocalDateTime;
=======
>>>>>>> parent of e92394e (Merge branch 'Rania')
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

<<<<<<< HEAD
public class ReservationTerrain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numRes;
    Boolean etatReser =true;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime  dateFin;
=======
public class ReservationTerrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numRes;
    LocalDate dateDebut;
    LocalDate dateFin;
>>>>>>> parent of e92394e (Merge branch 'Rania')
    @Enumerated(EnumType.STRING)
    TypeReservation typeRes;
    Double PrixReser;
    //@JsonIgnore
    @ManyToOne
    User user;
<<<<<<< HEAD
    //@JsonIgnore
=======
>>>>>>> parent of e92394e (Merge branch 'Rania')
    @ManyToOne
    Terrain terrain;
    @OneToOne
    Tournoi tournoi;

}
