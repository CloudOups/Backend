package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.PanierElement;

public interface IPanierElementRepository extends JpaRepository<PanierElement, Long> {
}