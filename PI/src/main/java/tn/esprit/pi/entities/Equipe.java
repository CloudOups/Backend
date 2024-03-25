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
    @ManyToOne
    Tournoi tournoi;
    @OneToOne(cascade = CascadeType.PERSIST )
    Equipe equipe;
}
