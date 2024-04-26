package tn.esprit.pi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Commande;


public interface ICommandeRepository extends JpaRepository<Commande, Long> {
   // Page<Commande> findByUserEmailOrderByDateCreationDesc(String email, Pageable pageable);
    Page<Commande> findCommandeByUserEmailOrderByDateCreationDesc(String email, Pageable pageable);
}