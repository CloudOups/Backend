package tn.esprit.pi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Produit;

import java.util.List;

public interface IProduitRepository extends JpaRepository<Produit, Long> {
    //List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name , String description);

    Page<Produit> findByNomProdContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyord1, String keyord2, Pageable pageable);
   // Object findAll(Pageable pageable);
}
