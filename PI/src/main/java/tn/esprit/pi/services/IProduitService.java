package tn.esprit.pi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.pi.entities.Produit;

import java.util.List;

public interface IProduitService
{
    Produit addProduit(Produit produit);
    Produit updateProduit(Produit produit);
    void delete(Long numProd);
    Produit getById(Long numProd);

    List<Produit> getAll();
    Page<Produit> getAllPaggination(Pageable pageable);

    Page<Produit> findByNomAndDescription(String keyord1, Pageable pageable);
}
