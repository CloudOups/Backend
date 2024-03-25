package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @Enumerated(EnumType.STRING)
     TypeAbonn typeAbonn;
    @ManyToOne
    CoursSport coursSport;

}
