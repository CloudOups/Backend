package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Commande;

public interface ICommandeRepositoy extends JpaRepository<Commande, Long> {
}
