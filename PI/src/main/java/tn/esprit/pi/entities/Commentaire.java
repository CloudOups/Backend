package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idcmt;
    String contenucm;
    LocalDate datecm;
    @ManyToOne
    User user;
    @ManyToOne(cascade = CascadeType.ALL)
    Publication publication;
}
