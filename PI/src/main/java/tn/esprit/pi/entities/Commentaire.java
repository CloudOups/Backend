package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

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
    Date datecm;
    @ManyToOne
    User user;
    @ManyToOne(cascade = CascadeType.ALL)
    Publication publication;
    String Sentiment;
}
