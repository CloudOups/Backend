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

public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numequipe;
    String nomEquipe;
    int classement;
    @OneToOne(cascade = CascadeType.PERSIST )
    User chef;
    @ManyToOne
    Tournoi tournoi;
}
