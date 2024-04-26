package tn.esprit.pi.dto;


import lombok.*;
import tn.esprit.pi.entities.Commande;
import tn.esprit.pi.entities.PanierElement;
import tn.esprit.pi.entities.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Achat {
    private User utilisateur;
    private Commande commande;
    private Set<PanierElement> commandeElements = new HashSet<>();
}
