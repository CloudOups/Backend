package tn.esprit.pi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "Commande_id")
    Long CommandeId ;
    String numeroSuiviCommande;
    double totalprix;
    String status;
    @CreationTimestamp
    LocalDate dateCreation;
    @UpdateTimestamp
    LocalDate dateDernierModification;

    String modePaiement = "Cash";
    String adresseLivraison;
    String numeroCarte = "0000 0000 0000 0000";

    @ManyToOne
    @JoinColumn(name = "client_id")
    User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    Set<PanierElement> panierItems = new HashSet<>();

    // add a item to the panierItems customized method
    public void add(PanierElement item) {

        // if the item is not null
        if(item != null) {
            // if the panierItems is null, initialize it
            if(panierItems == null) {
                // initialize the panierItems
                panierItems = new HashSet<>();
            }
            // add the item to the panierItems
            panierItems.add(item);
            // set the item's commande to this
            item.setCommande(this);
        }
    }
}
