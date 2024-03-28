package tn.esprit.pi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String nom;
    String prenom;
    String email;
    String mdp;
    @Enumerated(EnumType.STRING)
    Role role;
    String photo;
    String classe;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<ReservationTerrain> reservationTerrains;

    @OneToOne(mappedBy ="user")
    Ticket ticket;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
    Set<Commentaire> Commentaires;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Publication> publications;

    @OneToOne(mappedBy = "user")
    Panier panier;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Commande> commandes;
    /*@ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;*/
 }
