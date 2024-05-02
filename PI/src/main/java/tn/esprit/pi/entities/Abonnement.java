package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numAbonn;
    String nomAbonn;
    LocalDate dateDebut;
    LocalDate dateFin;
    Double prixAbonn;
    @Enumerated(EnumType.STRING)
    TypeAbonn typeAbonn;
    @ManyToOne
    CoursSport coursSport;
    @OneToOne(cascade = CascadeType.PERSIST )
    User user;
    boolean paye;


}


