package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
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
    @OneToOne
     Abonnement abonnement;
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="user")
    Set<Commentaire> Commentaires;
    @OneToMany(cascade = CascadeType.ALL)
    Set<Publication> publications;
    @OneToOne
    Panier panier;
    @OneToOne
    Ticket ticket;
    @OneToOne
    Equipe equipe;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<ReservationTerrain> reservations;
    @OneToMany(cascade = CascadeType.ALL)
    Set<Commande> commands;
}
