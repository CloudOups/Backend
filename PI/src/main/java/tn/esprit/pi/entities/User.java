package tn.esprit.pi.entities;

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
    int userId;
    String nom;
    String prenom;
    String email;
    String motDePasse;
    @Enumerated(EnumType.STRING)
    Role role;
    String photo;
    String classe;
    @OneToOne(cascade = CascadeType.PERSIST )
    Abonnement abonnement;
    @OneToMany(mappedBy = "user")
    Set<ReservationTerrain> reservationTerrains;
    @OneToOne(cascade = CascadeType.PERSIST )
    Equipe equipe;
    @OneToOne
    Ticket ticket;
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
    Set<Commentaire> Commentaires;
    @OneToMany(cascade = CascadeType.ALL)
    Set<Publication> publications;
    @OneToOne
    Panier panier;
    @OneToMany(cascade = CascadeType.ALL)
    Set<Commande> commandes;
}
