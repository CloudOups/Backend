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
public class PanierElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String imageUrl ;
    float prixUnitaire;
     /* @OneToOne
    User user; */
    int quantite;
    @JoinColumn(name = "Produit_id")
    Long numProd;

    @ManyToOne
    @JoinColumn(name = "Commande_id")
    private Commande commande;

}
