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

public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long numProd;
    String nomProd;
    float Prix;
    int quantite;
    String image;
    String description;
    boolean actif;
   // @ManyToMany(mappedBy = "produit",cascade = CascadeType.ALL)
   // Set<PanierElement> paniers;

}
