package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CoursSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idcours;
    @Enumerated(EnumType.STRING)
    TypeSport typeSport;
    String description;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "coursSport")
    Set<Abonnement> abonnements;
}
