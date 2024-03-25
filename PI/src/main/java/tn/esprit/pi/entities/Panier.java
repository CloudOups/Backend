package tn.esprit.pi.entities;

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
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idPanier;
    int nbItem;
    float totalPrix;
    @OneToOne(mappedBy = "panier")
    User user;
    @ManyToMany
    Set<Produit> produits;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "panier")
    Set<Commande> commands;

}
