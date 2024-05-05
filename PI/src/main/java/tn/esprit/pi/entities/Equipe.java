package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class Equipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numequipe;
    String nomEquipe;
    int classement;
    int nbMemEquipe;
    @OneToOne
    private User chefEquipe;
    @ManyToOne
    Tournoi tournoi;

    // Relation Many-to-Many avec les membres de l'équipe
    @ManyToMany
    @JoinTable(
            name = "membres_equipe",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
     Set<User> membresEquipe;

    // Relation Many-to-Many avec les membres en attente
    @ManyToMany
    @JoinTable(
            name = "pending_members_equipe",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
     Set<User> membresEnAttente;
}
