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

public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int IdProd;
    String nomProd;
    float Prix;
    int quantite;
    String image;
    @ManyToMany(mappedBy = "produits",cascade = CascadeType.ALL)
    Set<Panier> paniers;

}
